package com.iee.file.propsLoad.enumtest;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @ClassName PropsLoad
 * @Description 当属性为枚举类型是，加载成一个bean的方式
 * @Author longxn
 * @Date 2018/7/6 15:47
 */
@ConfigurationProperties(prefix = "acme2.hehe")
@Component
public class AcmeEnumProperties {
    //acme2:
    //  hehe: #bean的名字
    //    name: my name
    //    description: fail
    private String name;
    // acme2.hehe下的description必须是DescpEnum下的某个值, 否则无法启动
    private DescpEnum description = DescpEnum.success;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DescpEnum getDescription() {
        return description;
    }

    public void setDescription(DescpEnum description) {
        this.description = description;
    }
}
