package com.lognxn.test;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName LoadConfig
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/10/24 0024 16:58
 */
@ConfigurationProperties(prefix = "opc")
@Component
public class LoadConfig {

    List<String> aa;

    public List<String> getAa() {
        return aa;
    }

    public void setAa(List<String> aa) {
        this.aa = aa;
    }
}
