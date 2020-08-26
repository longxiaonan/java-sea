# 技术栈

### 多数据源配置

```yaml
spring:
  datasource:
    #    type: com.alibaba.druid.pool.DruidDataSource
    ### 可读数据源
    select:
      jdbc-url: jdbc:mysql://127.0.0.1:3306/my
      driver-class-name: com.mysql.jdbc.Driver
      username: root
      password: 123456

    ### 可写数据源
    update:
      jdbc-url: jdbc:mysql://127.0.0.1:3306/my1
      driver-class-name: com.mysql.jdbc.Driver
      username: root
      password: 123456
```

### 配置类

两种方式来获取数据源

```java
@Configuration
public class DataSourceConfig {
    /** 创建可读数据源，方式一 */
    @Bean("selectDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.select")
//    @Qualifier("selectDataSource")
    public DataSource selectDataSource(){
        return DataSourceBuilder.create().build();
    }

    /** 创建可写数据源，方式二 */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.update")
    @Qualifier("updateDataSource")
    public DataSource updateDataSource(){
        return DataSourceBuilder.create().build();
    }
}
```

### 线程的数据源缓存

通过ThreadLocal来缓存线程的数据源类型

```java
@Component
@Lazy(false)
public class DataSourceContextHolder {
    //采用ThreadLocal保存本地多数据源
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    //设置数据源类型
    public static void setDbType(String dbType){
        contextHolder.set(dbType);
    }

    public static String getDbType(){
        return contextHolder.get();
    }

    public static void clearDbType(){
        contextHolder.remove();
    }

}
```

### 动态数据源获取类

在请求数据库时，会调用determineCurrentLookupKey方法去线程缓存（DataSourceContextHolder）中获取线程的数据源，线程的数据源通过aop存放到（DataSourceContextHolder）中。

```java
@Component
@Primary
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Autowired
    @Qualifier("selectDataSource")
    private DataSource selectDataSource;

    @Autowired
    @Qualifier("updateDataSource")
    private DataSource updateDataSource;

    /**
     * 这个是主要的方法，返回的是生效的数据源名称
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        System.out.println("DataSrouceContextHolder..." + DataSourceContextHolder.getDbType());
        return DataSourceContextHolder.getDbType();
    }

    /**
     * 配置数据源信息
     */
    @Override
    public void afterPropertiesSet(){
        Map<Object,Object> map = new HashMap<>();
        map.put("selectDataSource",selectDataSource);
        map.put("updateDataSource",updateDataSource);
        setTargetDataSources(map);
        setDefaultTargetDataSource(updateDataSource);
        super.afterPropertiesSet();
    }

}
```

### aop实现访问的时候设置线程的数据源

线程的数据源通过aop存放到（DataSourceContextHolder）中

```java
@Aspect
@Component
@Lazy(false)
@Order(0)// order设定AOP指定顺序，使之在数据库事务上先执行
public class SwitchDataSourceAOP {

    Logger log = LoggerFactory.getLogger(SwitchDataSourceAOP.class);

    /** 这里切换到你的方法目录 */
    @Before("execution(* com.javasea.orm.rw..*Service.*(..))")
    public void process(JoinPoint joinPoint){
        System.out.println("SwitchDataSourceAOP ...>>>>>>>>>....");
        String methodName = joinPoint.getSignature().getName();
        if(methodName.startsWith("get") || methodName.startsWith("count") || methodName.startsWith("find")
            || methodName.startsWith("list") || methodName.startsWith("select")){
            log.info("______________selectDataSource");
            DataSourceContextHolder.setDbType("selectDataSource");
        }else{
            //切换dataSource
            log.info("______________updateDataSource");
            DataSourceContextHolder.setDbType("updateDataSource");
        }

    }

}
```



