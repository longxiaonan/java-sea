package com.iee.common.utils.thirdsupport;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;

/**
 * 简易DateConverter.
 * 供Apache BeanUtils 做转换,默认时间格式为yyyy-MM-dd,可由构造函数改变.
 * Created by ${denghb} on 2016/9/6.
 */
public class DateConverter implements Converter {
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public DateConverter(String formatPattern) {
        if (StringUtils.isNotBlank(formatPattern)) {
            format = new SimpleDateFormat(formatPattern);
        }
    }
    @Override
    public Object convert(Class aClass, Object value) {
        String dateStr = (String) value;
        if (StringUtils.isNotBlank(dateStr)) {
            try {
                return format.parse(dateStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}