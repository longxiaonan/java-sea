package com.javasea.orm.springdata.multidatasource.config;

import com.javasea.orm.springdata.multidatasource.entity.backup.Order;
import com.javasea.orm.springdata.multidatasource.entity.primary.Product;
import com.javasea.orm.springdata.multidatasource.repository.backup.OrderRepository;
import com.javasea.orm.springdata.multidatasource.repository.primary.ProductRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.SchemaManagement;
import org.springframework.boot.jdbc.SchemaManagementProvider;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author F嘉阳
 * @date 2019-03-30 9:24
 */
@Configuration
public class DataAccessConfig {

    private final ObjectProvider<SchemaManagementProvider> providers;

    private final HibernateProperties hibernateProperties;

    private final JpaProperties properties;

    private final ObjectProvider<PhysicalNamingStrategy> physicalNamingStrategy;

    private final ObjectProvider<ImplicitNamingStrategy> implicitNamingStrategy;

    private final ConfigurableListableBeanFactory beanFactory;

    private final ObjectProvider<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers;

    @Autowired
    public DataAccessConfig(ObjectProvider<SchemaManagementProvider> providers, HibernateProperties hibernateProperties, JpaProperties properties, ObjectProvider<PhysicalNamingStrategy> physicalNamingStrategy, ObjectProvider<ImplicitNamingStrategy> implicitNamingStrategy, ConfigurableListableBeanFactory beanFactory, ObjectProvider<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers) {
        this.providers = providers;
        this.hibernateProperties = hibernateProperties;
        this.properties = properties;
        this.physicalNamingStrategy = physicalNamingStrategy;
        this.implicitNamingStrategy = implicitNamingStrategy;
        this.beanFactory = beanFactory;
        this.hibernatePropertiesCustomizers = hibernatePropertiesCustomizers;
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.first")
    public DataSourceProperties firstDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.first.configuration")
    public HikariDataSource firstDataSource() {
        return firstDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.second")
    public DataSourceProperties secondDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.second.configuration")
    public HikariDataSource secondDataSource() {
        return secondDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean firstEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("firstDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                // 加入规约配置
                .properties(getVendorProperties(dataSource))
                .packages(Product.class)
                .persistenceUnit("first")
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean secondEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("secondDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .properties(getVendorProperties(dataSource))
                .packages(Order.class)
                .persistenceUnit("second")
                .build();
    }

    @Bean
    public PlatformTransactionManager primaryTransactionManager(
            @Qualifier("firstEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }

    @Bean
    public PlatformTransactionManager backupTransactionManager(
            @Qualifier("secondEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }

    @EnableJpaRepositories(basePackageClasses = ProductRepository.class,
            entityManagerFactoryRef = "firstEntityManagerFactory", transactionManagerRef = "primaryTransactionManager")
    @Primary
    public class PrimaryConfiguration {
    }

    @EnableJpaRepositories(basePackageClasses = OrderRepository.class,
            entityManagerFactoryRef = "secondEntityManagerFactory", transactionManagerRef = "backupTransactionManager")
    public class secondConfiguration {
    }

    /**
     * 获取配置文件信息
     *
     * @param dataSource
     * @return
     */
    private Map<String, Object> getVendorProperties(DataSource dataSource) {
        List<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers = determineHibernatePropertiesCustomizers(
                physicalNamingStrategy.getIfAvailable(),
                implicitNamingStrategy.getIfAvailable(), beanFactory,
                this.hibernatePropertiesCustomizers.orderedStream()
                        .collect(Collectors.toList()));
        Supplier<String> defaultDdlMode = () -> new HibernateDefaultDdlAutoProvider(providers)
                .getDefaultDdlAuto(dataSource);
        return new LinkedHashMap<>(this.hibernateProperties.determineHibernateProperties(
                properties.getProperties(),
                new HibernateSettings().ddlAuto(defaultDdlMode)
                        .hibernatePropertiesCustomizers(
                                hibernatePropertiesCustomizers)));
    }

    /**
     * 命名策略自动判断
     *
     * @param physicalNamingStrategy
     * @param implicitNamingStrategy
     * @param beanFactory
     * @param hibernatePropertiesCustomizers
     * @return
     */
    private List<HibernatePropertiesCustomizer> determineHibernatePropertiesCustomizers(
            PhysicalNamingStrategy physicalNamingStrategy,
            ImplicitNamingStrategy implicitNamingStrategy,
            ConfigurableListableBeanFactory beanFactory,
            List<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers) {
        List<HibernatePropertiesCustomizer> customizers = new ArrayList<>();
        if (ClassUtils.isPresent(
                "org.hibernate.resource.beans.container.spi.BeanContainer",
                getClass().getClassLoader())) {
            customizers
                    .add((properties) -> properties.put(AvailableSettings.BEAN_CONTAINER,
                            new SpringBeanContainer(beanFactory)));
        }
        if (physicalNamingStrategy != null || implicitNamingStrategy != null) {
            customizers.add(new NamingStrategiesHibernatePropertiesCustomizer(
                    physicalNamingStrategy, implicitNamingStrategy));
        }
        customizers.addAll(hibernatePropertiesCustomizers);
        return customizers;
    }

    /**
     * 自动进行建表操作
     */
    class HibernateDefaultDdlAutoProvider implements SchemaManagementProvider {

        private final Iterable<SchemaManagementProvider> providers;

        HibernateDefaultDdlAutoProvider(Iterable<SchemaManagementProvider> providers) {
            this.providers = providers;
        }

        public String getDefaultDdlAuto(DataSource dataSource) {
            if (!EmbeddedDatabaseConnection.isEmbedded(dataSource)) {
                return "none";
            }
            SchemaManagement schemaManagement = getSchemaManagement(dataSource);
            if (SchemaManagement.MANAGED.equals(schemaManagement)) {
                return "none";
            }
            return "create-drop";

        }

        @Override
        public SchemaManagement getSchemaManagement(DataSource dataSource) {
            return StreamSupport.stream(this.providers.spliterator(), false)
                    .map((provider) -> provider.getSchemaManagement(dataSource))
                    .filter(SchemaManagement.MANAGED::equals).findFirst()
                    .orElse(SchemaManagement.UNMANAGED);
        }

    }

    private static class NamingStrategiesHibernatePropertiesCustomizer
            implements HibernatePropertiesCustomizer {

        private final PhysicalNamingStrategy physicalNamingStrategy;

        private final ImplicitNamingStrategy implicitNamingStrategy;

        NamingStrategiesHibernatePropertiesCustomizer(
                PhysicalNamingStrategy physicalNamingStrategy,
                ImplicitNamingStrategy implicitNamingStrategy) {
            this.physicalNamingStrategy = physicalNamingStrategy;
            this.implicitNamingStrategy = implicitNamingStrategy;
        }

        /**
         * 数据库命名映射策略
         *
         * @param hibernateProperties the JPA vendor properties to customize
         */
        @Override
        public void customize(Map<String, Object> hibernateProperties) {
            if (this.physicalNamingStrategy != null) {
                hibernateProperties.put("hibernate.physical_naming_strategy",
                        this.physicalNamingStrategy);
            }
            if (this.implicitNamingStrategy != null) {
                hibernateProperties.put("hibernate.implicit_naming_strategy",
                        this.implicitNamingStrategy);
            }
        }
    }


}

