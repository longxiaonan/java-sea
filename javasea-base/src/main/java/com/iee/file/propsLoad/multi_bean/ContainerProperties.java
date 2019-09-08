package com.iee.file.propsLoad.multi_bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @ClassName PropsLoad
 * @Description 加载成多个对象
 * @Author longxn
 * @Date 2018/7/6 15:47
 */
@ConfigurationProperties(prefix = "containerconfig")
@Component
public class ContainerProperties {

    private final BaseService msgqueue = new BaseService();
    private final BaseService memory = new BaseService();
    private final BaseService storage = new BaseService();
    private final BaseService archive = new BaseService();

    public BaseService getMsgqueue() {
        return msgqueue;
    }

}
