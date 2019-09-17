
package com.iee.common.entity;

import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.google.gson.annotations.JsonAdapter;
import com.iee.json.gson.GsonTest;
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
