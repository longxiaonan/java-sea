package com.iee.file.propsLoad.array;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ArrayProps
 * @Description 将配置文件加载array
 * @Author longxiaonan@163.com
 * @Date 2018/8/8 17:20
 */
@ConfigurationProperties(prefix = "acme3")
@Component
public class ListProps {

    private List<String> array = new ArrayList<>();

    public List<String> getArray() {
        return array;
    }
}
