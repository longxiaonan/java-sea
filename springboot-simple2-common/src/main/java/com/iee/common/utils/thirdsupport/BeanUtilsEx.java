package com.iee.common.utils.thirdsupport;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import java.lang.reflect.InvocationTargetException;

public class BeanUtilsEx extends BeanUtils {
    public static void copyProperties(Object dest, Object orig) {
        try {
            BeanUtils.copyProperties(dest, orig);
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }
    static {
        ConvertUtils.register(new DateConverter(null), java.util.Date.class);
        //ConvertUtils.register(new BigDecimalConvert(), BigDecimal.class);
    }
}