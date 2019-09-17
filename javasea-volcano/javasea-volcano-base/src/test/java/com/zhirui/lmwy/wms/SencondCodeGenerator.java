package com.zhirui.lmwy.wms;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 生成基本的entity，mapper，controller和service类等。
 */
public class SencondCodeGenerator {

    @Test
    public void myGenerator() {
        //1 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        globalConfig.setActiveRecord(false) //是否支持AR模式
                .setAuthor("longxiaonan")    //作者
                .setOutputDir(projectPath + "/src/main/java" + "/com/zhirui/lmwy/wms")//输出目录
//                .setActiveRecord(true)// 开启 activeRecord 模式
                .setEnableCache(false)// XML 二级缓存
                .setFileOverride(true)  //文件覆盖
                .setIdType(IdType.AUTO) //主键策略 自增
                .setServiceName("%sService")    //设置生成的servic接口的名字首字目去除I
                .setBaseResultMap(true)     //生成sql mapper映射文件
                .setBaseColumnList(true);

        //2 数据库配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)    //设置数据库类型
                .setDriverName("com.mysql.jdbc.Driver")      //设置数据驱动
                .setUrl("jdbc:mysql://127.0.0.1:3306/my?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8")         //设置数据库url
                .setUsername("root")    //设置用户名
                .setPassword("123456");    //设置密码

        //3 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true) //全局大写命名
//                    .setDbColumnUnderline(true) //指定表名字段名是否使用下划线
                .setNaming(NamingStrategy.underline_to_camel)   //数据库表映射到实体的命名方法：驼峰命名法
                // 【实体】是否为lombok模型（默认 false）
                .setEntityLombokModel(true)
                // .setInclude(new String[] { "user" }) // 需要生成的表
                // .setExclude(new String[]{"test"}) // 排除生成的表
                .setTablePrefix(new String[]{"t_","wht","wh"}) //表的前缀
                .setInclude(new String[]{"t_user"});    //需要生成的表

        //4 包名策略配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("")      //全局父包
                .setController("controller")
                .setService("service")
                .setServiceImpl("service.impl")
                .setMapper("mapper")    //dao接口
                .setXml("mapper")       //mapper映射文件
                .setEntity("entity");


//        //5 自定义配置
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//                // to do nothing
//            }
//        };
//        // 自定义输出配置
//        List<FileOutConfig> focList = new ArrayList<>();
//        // 自定义配置会被优先输出
//        focList.add(new FileOutConfig() {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return projectPath + "/src/main/resources/mapper/" + packageConfig.getModuleName()
//                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//            }
//        });
//        cfg.setFileOutConfigList(focList);


        //6 整合配置
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig);

        //6 执行
        autoGenerator.execute();

    }

}
