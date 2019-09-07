package com.zhirui.lmwy.wms.demo.enumm;

/**
 * @ClassName TestEnumType
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/9/5 0005 23:11
 */
public class TestEnumType {
    public static void main(String[] args) {
        System.out.println(EnumType.getList());//[{value=入库, key=01}, {value=出库, key=02}]
        System.out.println(EnumType.getValue("01"));//入库
        System.out.println(EnumType.values());//[Lcom.zhirui.lmwy.wms.demo.enumm.EnumType;@5197848c
        System.out.println(EnumType.ORDERENTRY);//ORDERENTRY
        System.out.println(EnumType.ORDERENTRY.getValue());//入库
        System.out.println(EnumType.ORDERENTRY.getKey());//01
    }
}
