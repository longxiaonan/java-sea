package com.iee.redis.entity;

import java.io.Serializable;

public class Person implements Serializable {
    // 为了能够正确得序列化和反序列化,这个属性必不可少
    private static final long serialVersionUID = -1L;
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
