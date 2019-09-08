package com.iee.file.propsLoad.innerClass;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName ScProperties
 * @Description SC为服务的内部服务配置类
 * @Author longxiaonan@163.com
 * @Date 2018/7/27 10:43
 */
@ConfigurationProperties(prefix = "sc.server")
@Component
public class ScProps {

    private final Despatch despatch = new Despatch();//LIST,MAP,实体
    private final Resend resend = new Resend();
    private final Preprocess preprocess = new Preprocess();
    private final Devconnect devconnect = new Devconnect();
    private final TransparentTransfer transparentTransfer =new TransparentTransfer();

    public Despatch getDespatch() {
        return despatch;
    }

    public Resend getResend() {
        return resend;
    }

    public Preprocess getPreprocess() {
        return preprocess;
    }

    public Devconnect getDevconnect() {
        return devconnect;
    }

    public TransparentTransfer getTransparentTransfer() {
        return transparentTransfer;
    }

    @Data
    public static abstract class QueueParam{
        private Integer threadNum;
        private Integer queueDepth;
    }

    public static class Despatch extends QueueParam {
    }

    public static class Resend extends QueueParam {
    }

    public static class Preprocess extends QueueParam {
    }

    @Data
    public static class Devconnect {
        private String agentUrl;
        private String cmdUrl;
    }

    @Data
    public static class TransparentTransfer {
        private Boolean open;
        private String tthost;
        private String ids;
    }

}
