package com.iee.common.randomnumber;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName randomdemo2
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/11/20 0020 14:30
 */
public class randomdemo2 {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            int hashCodeV = UUID.randomUUID().toString().hashCode();
            if (hashCodeV < 0) {//有可能是负数
                hashCodeV = -hashCodeV;
            }
            // 0 代表前面补充0
            // 4 代表长度为4
            // d 代表参数为正数型
            System.out.println(String.format("%011d", hashCodeV));
        }
    }

    public static String getOrderIdByUUId() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String time = format.format(date);
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return time + String.format("%011d", hashCodeV);
    }
}
