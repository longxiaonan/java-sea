package com.zhirui.lmwy.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.cglib.beans.BeanCopier;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description  高效beancopy, int类型和Integer类型是不同的类型，不进行拷贝。
 * BeanCopier只拷贝名称和类型都相同的属性。
 * @Author longxiaonan@163.com
 * @Date 11:39 2019/9/6 0006
 **/
@Slf4j
public class BeanCopyUtil {
     //使用缓存提高效率
    private static final ConcurrentHashMap<String, BeanCopier> beanCopierMap = new ConcurrentHashMap<>();

    private static Lock initLock = new ReentrantLock();

    /** copy source对象，通过targetClass创建target对象，属性copy后返回target
     * @param source 源对象
     * @param targetClass 目标对象类型
     * @return 属性copy后的target
     */
    public static <O, T> T mapper(O source, Class<T> targetClass) {
        T instance = baseMapper(source, targetClass);
        return instance;
    }

    public static <O, T> T mapper(O source, Class<T> target, IAction<T> action) {
        T instance = baseMapper(source, target);
        action.run(instance);
        return instance;
    }

    /**
     * Pojo 类型通过list转换（浅复制，字段名&类型相同则被复制）
     * @param source 存有pojo的的list
     * @param targetClass 目标类型class
     * @return 目标类型的对象list
     */
    public static <E, S> List<E> mapperList(List<S> source, Class<E> targetClass) {
        if (source == null) {
            return null;
        }
        try {
            if (source.isEmpty()) {
                return source.getClass().newInstance();
            }
            List result = source.getClass().newInstance();

            for (S each : source) {
                result.add(mapper(each, targetClass));
            }
            return result;
        } catch (Exception e) {
            log.error("对象拷贝失败,{}", e);
            throw new RuntimeException("对象拷贝失败" + source + "_" + targetClass);
        }
    }

    /** 将源对象的属性 拷贝 到目标对象的属性中，只有属性名和类型都对应的才会被拷贝, 如果源对象的值是null，那么也会被覆盖到目标对象
     * @param source 源对象
     * @param target 目标对象
     * @return 目标对象
     */
    public static <O, T> T mapperObject(O source, T target) {
        String baseKey = generateKey(source.getClass(), target.getClass());
        BeanCopier copier = getBeanCopier(source.getClass(), target.getClass());
        copier.copy(source, target, null);
        return target;
    }

    public static <O, T> T mapperObject(O source, T target, IAction<T> action) {
        String baseKey = generateKey(source.getClass(), target.getClass());
        BeanCopier copier = getBeanCopier(source.getClass(), target.getClass());
        copier.copy(source, target, null);
        action.run(target);
        return target;
    }

    /** 将源对象的属性 拷贝 到目标对象的属性中，只有属性名和类型都对应的才会被拷贝, 如果源对象的值是null，那么会忽略掉，不覆盖目标属性。
     * @param source 源对象
     * @param target 目标对象
     * @return
     */
    public static <O, T> T mapperObjectIgnoreNull( O source, T target){
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
        return  target;
    }

    /** 获取源对象中属性值是null的属性名， 用于 方法mapperObjectIgnoreNull 中过滤掉为null的属性
     * @param source 源对象
     * @return 源对象中为null的属性名
     */
    private static String[] getNullPropertyNames ( Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    private static <O, T> T baseMapper(O source, Class<T> target) {
        String baseKey = generateKey(source.getClass(), target);
        BeanCopier copier = getBeanCopier(source.getClass(), target);
        T instance = null;
        try {
            instance = target.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.error("mapper 创建对象异常" + e.getMessage());
        }
        copier.copy(source, instance, null);
        return instance;
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.getName() + "_" + class2.getName();
    }

    @FunctionalInterface
    private static interface IAction<T> {
        void run(T t);
    }

    /**
     * 初始化 BeanCopier
     * @param source
     * @param target
     * @return
     */
    private static BeanCopier initCopier(Class source, Class target) {
        initLock.lock();
        BeanCopier find = beanCopierMap.get(generateKey(source, target));
        if (find != null) {
            initLock.unlock();
            return find;
        }
        BeanCopier beanCopier = BeanCopier.create(source, target, false);
        beanCopierMap.put(generateKey(source, target), beanCopier);
        initLock.unlock();
        return beanCopier;
    }

    /**
     * 获取BeanCopier
     * @param source
     * @param target
     * @return
     */
    private static BeanCopier getBeanCopier(Class source, Class target) {
        BeanCopier beanCopier = beanCopierMap.get(generateKey(source, target));
        if (beanCopier != null) {
            return beanCopier;
        }
        return initCopier(source, target);
    }
}
