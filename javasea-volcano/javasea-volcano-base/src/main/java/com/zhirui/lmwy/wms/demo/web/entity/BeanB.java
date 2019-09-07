package com.zhirui.lmwy.wms.demo.web.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BeanB {
    private String name1;
    private String name2;
    private List<String> nameList;
    private Date birthday;
}
