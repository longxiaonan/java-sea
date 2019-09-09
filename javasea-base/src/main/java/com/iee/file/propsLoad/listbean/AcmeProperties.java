package com.iee.file.propsLoad.listbean;

import com.iee.common.entity.MyPojo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName AcmeProperties
 * @Description 将配置文件加载成复合类型
 * @Author longxn
 * @Date 2018/7/6 15:47
 */
@ConfigurationProperties(prefix = "acme")
@Component
public class AcmeProperties  {

    private final List<MyPojo> list = new ArrayList<>();

    public List<MyPojo> getList() {
        return this.list;
    }
}
