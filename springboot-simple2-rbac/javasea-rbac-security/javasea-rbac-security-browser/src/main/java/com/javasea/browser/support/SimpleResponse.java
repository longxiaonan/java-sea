package com.javasea.browser.support;

/**
 * @ClassName SimpleResponse
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/6/28 0028 22:42
 */
public class SimpleResponse {
    private Object content;

    public SimpleResponse(Object content) {
        this.content = content;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
