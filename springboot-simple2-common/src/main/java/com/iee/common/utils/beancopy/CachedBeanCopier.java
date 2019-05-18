package com.iee.common.utils.beancopy;

import org.springframework.cglib.beans.BeanCopier;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CachedBeanCopier
 * @Description 类型不一致(int 和 Integer)，set，get不一致
 * @Author longxiaonan@163.com
 * @Date 2019/5/10 0010 20:08
 */
public class CachedBeanCopier {

    static final Map<String, BeanCopier> BEAN_COPIERS = new HashMap<String, BeanCopier>();

    public static void copy(Object srcObj, Object destObj) {
        String key = genKey(srcObj.getClass(), destObj.getClass());
        BeanCopier copier = null;
        if (!BEAN_COPIERS.containsKey(key)) {
            copier = BeanCopier.create(srcObj.getClass(), destObj.getClass(), false);
            BEAN_COPIERS.put(key, copier);
        } else {
            copier = BEAN_COPIERS.get(key);
        }
        copier.copy(srcObj, destObj, null);
    }

    private static String genKey(Class<?> srcClazz, Class<?> destClazz) {
        return srcClazz.getName() + destClazz.getName();
    }

    public interface Converter {
        // value 源对象属性，target 目标对象属性类，context 目标对象setter方法名
        Object convert(Object value, Class target, Object context);
    }
    static class AccountConverter implements Converter {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @Override
        public Object convert(Object value, Class target, Object context) {
            if (value instanceof Integer) {
                return (Integer) value;
            } else if (value instanceof Timestamp) {
                Timestamp date = (Timestamp) value;
                return sdf.format(date);
            } else if (value instanceof BigDecimal) {
                BigDecimal bd = (BigDecimal) value;
                return bd.toPlainString();
            }
            return null;
        }
    }

}
