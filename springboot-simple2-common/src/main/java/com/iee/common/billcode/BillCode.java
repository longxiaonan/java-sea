package com.iee.common.billcode;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName BillCode
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/11/20 0020 11:05
 */
public class BillCode {

    public static void main(String[] args) {
        String sc = generateBillCode("SC", "1234567890");
        System.out.println(sc);
    }

    /**
     * 自动生成单据编号，规则如：XSD+20170614+001，第二天的单据从001开始
     *
     * @param billType 单据类型如"XSD",DbBillCode  "数据库中取出来的单据号"
     * @return
     */
    public static String generateBillCode(String billType, String DbBillCode) {

//存放最终生成的单据编号的字符串
        String billCode = new String();

        String dateString = new SimpleDateFormat("yyyyMMdd").format(new Date());

        if ("".equals(DbBillCode) || DbBillCode == null) {
//如果单号不存在,则设置每天的第一个单号
            billCode = billType + dateString + "001";
        } else {

//取出单据号中的固定位
            String str = billType + DbBillCode.substring(0, 8);

//取出流水号
            String temp = DbBillCode.substring(DbBillCode.length() - 3, DbBillCode.length());


//取出当天的所有单号中最大的单号截取后自增1
            if (Integer.parseInt(temp) >= 1 && Integer.parseInt(temp) < 999) {
                temp = String.valueOf(Integer.parseInt(temp) + 1);
            }
            switch (temp.length()) {
                case 1:
                    temp = "00" + temp;
                    break;
                case 2:
                    temp = "0" + temp;
                    break;
                default:
                    break;
            }
            billCode = str + temp;
        }

        System.out.println(billCode);
        return billCode;
    }
}
