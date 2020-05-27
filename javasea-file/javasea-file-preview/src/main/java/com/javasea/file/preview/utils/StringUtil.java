package com.javasea.file.preview.utils;

/**
 * Created by lenovo12 on 2018/8/18.
 */
public final class StringUtil {
    private StringUtil(){}

    public static boolean isEmpty(String target){
        return "".equals(target) || null ==target;
    }
}
