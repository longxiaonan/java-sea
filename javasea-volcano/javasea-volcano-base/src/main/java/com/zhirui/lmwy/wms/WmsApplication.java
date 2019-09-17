package com.zhirui.lmwy.wms;

import com.zhirui.lmwy.common.utils.PrintApplicationInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 用于展示Spring应用的最佳实践
 * @author longxiaonan@163.com
 * @since 2019-06-02
 */
@SpringBootApplication
@ComponentScan("com.zhirui.lmwy")
@MapperScan({"com.zhirui.lmwy.wms.**.mapper","com.zhirui.lmwy.wms.**.mapper"})
//@EnableDiscoveryClient
public class WmsApplication {

    public static void main(String[] args) {
        // 启动项目，准备工作提示
        PrintApplicationInfo.printTip();
        // 启动spring-boot-plus
        ConfigurableApplicationContext context = SpringApplication.run(WmsApplication.class, args);
        // 打印项目信息
        PrintApplicationInfo.print(context);
    }

}
