package com.iee.enumm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EnumType {

    ORDERENTRY("01", "入库"),
    ORDEROUT("02", "出库");


    private String key;
    private String value;

    public String getKey() {
        return key;
    }

//    public void setKey(String key) {
//        this.key = key;
//    }

    public String getValue() {
        return value;
    }

//    public void setValue(String value) {
//        this.value = value;
//    }

    private EnumType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    // 普通方法
    // 取得 value
    public static String getValue(String key) {
        for (EnumType item : EnumType.values()) {
            if (item.getKey().equals(key)) {
                return item.value;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String value = getValue("01");
        System.out.println(value);
    }

    // 把 enum 转为 list 使用
    public static List<Map> getList() {
        List<Map> list = new ArrayList();
        Map map = null;
        for (EnumType item : EnumType.values()) {
            map = new HashMap();
            map.put("key", item.getKey());
            map.put("value", item.getValue());

            list.add(map);
        }
        return list;
    }
}
