package com.iee.patterns.example.strategy.order.handler;

import com.iee.patterns.example.common.utils.BeanTool;
import com.iee.patterns.example.strategy.order.handler.handler.AbstractHandler;

import java.util.Map;

public class HandlerContext {
    private Map<String, Class> handlerMap;
    public HandlerContext(Map<String, Class> handlerMap) {
        this.handlerMap = handlerMap;
    }
    public AbstractHandler getInstance(String type) {
        Class clazz = handlerMap.get(type);
        if (clazz == null) {
            throw new IllegalArgumentException("not found handler for type: " + type);
        }
        return (AbstractHandler) BeanTool.getBean(clazz);
    }
}
