package com.zhirui.lmwy.wms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 用于展示Spring应用的最佳实践
 *
 * @since 2019-06-02
 */
@SpringBootApplication
@ComponentScan("com.zhirui.lmwy")
@MapperScan({"com.zhirui.lmwy.wms.**.mapper","com.zhirui.lmwy.wms.**.mapper"})
//@EnableDiscoveryClient
public class WmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WmsApplication.class, args);
    }

}
