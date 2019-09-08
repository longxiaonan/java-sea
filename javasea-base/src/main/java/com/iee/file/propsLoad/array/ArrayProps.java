package com.iee.file.propsLoad.array;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName ArrayProps
 * @Description 将配置文件加载array
 * @Author longxiaonan@163.com
 * @Date 2018/8/8 17:20
 */
@ConfigurationProperties(prefix = "acme3")
@Component
public class ArrayProps {

    private String[] array;

    public String[] getArray() {
        return array;
    }

    public void setArray(String[] array) {
        this.array = array;
    }
}
