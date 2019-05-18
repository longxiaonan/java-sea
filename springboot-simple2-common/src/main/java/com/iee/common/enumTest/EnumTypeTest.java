package com.iee.common.enumTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EnumTypeTest {

    ORDERENTRY("01", "入库"),
    ORDEROUT("02", "出库");


    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private EnumTypeTest(String key, String value) {
        this.key = key;
        this.value = value;
    }

    // 普通方法
    // 取得 value
    public static String getValue(int index) {
        for (EnumTypeTest item : EnumTypeTest.values()) {
            if (item.getKey().equals(index)) {
                return item.value;
            }
        }
        return null;
    }

    // 把 enum 转为 list 使用
    public static List<Map> getList() {
        List<Map> list = new ArrayList();
        Map map = null;
        for (EnumTypeTest item : EnumTypeTest.values()) {
            map = new HashMap();
            map.put("key", item.getKey());
            map.put("value", item.getValue());

            list.add(map);
        }
        return list;
    }
}
