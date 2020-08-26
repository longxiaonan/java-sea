package com.javasea.orm.rw.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 注释：
 *
 * @author 陈导
 * @date 2020/8/3 14:42
 */
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
