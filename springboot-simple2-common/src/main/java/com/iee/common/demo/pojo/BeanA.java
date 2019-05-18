
package com.iee.common.demo.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BeanA {
    private String name1;
    private String name2;
    private List<String> nameList;
    private Date birthday;
}
