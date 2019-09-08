package com.iee.file.propsLoad.bean;

import com.vdp.common.entity.MyPojo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @ClassName PropsLoad
 * @Description 加载成一个bean
 * @Author longxn
 * @Date 2018/7/6 15:47
 */
@ConfigurationProperties(prefix = "acme2")
@Component
public class AcmeProperties2 {

    private final MyPojo hehe = new MyPojo();

    public MyPojo getHehe() {
        return hehe;
    }
}
