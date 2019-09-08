package com.iee.file.propsLoad.multi_bean;

/**
 * @ClassName BaseService
 * @Description TODO
 * @Author longxn
 * @Date 2018/7/6 17:06
 */
public class BaseService {

    private boolean enabled;

    private String component;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }
}
