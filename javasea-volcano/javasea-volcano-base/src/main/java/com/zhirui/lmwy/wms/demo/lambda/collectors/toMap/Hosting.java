package com.zhirui.lmwy.wms.demo.lambda.collectors.toMap;

import lombok.Data;

@Data
public class Hosting {
    private int Id;
    private String name;
    private long websites;
    public Hosting(int id, String name, long websites) {
        Id = id;
        this.name = name;
        this.websites = websites;
    }
}
