package com.iee.file.propsLoad.listmap;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @ClassName PropsLoad
 * @Description 加载成复合类型
 * @Author longxn
 * @Date 2018/7/6 15:47
 */
@ConfigurationProperties(prefix = "acme1")
public class AcmeProperties1 {

    private final List<Map<String,String>> list1 = new ArrayList<>();

    public List<Map<String, String>> getList1() {
        return list1;
    }
}
