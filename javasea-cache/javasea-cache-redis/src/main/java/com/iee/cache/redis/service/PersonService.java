package com.iee.cache.redis.service;

import com.iee.cache.redis.entity.Person;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private Person p1 = new Person(1, "Steve", "jobs");
    private Person p2 = new Person(2, "bill", "gates");
    private Person p3 = new Person(3, "unknown", "unknown");
    //* @Cacheable : Spring在每次执行前都会检查Cache中是否存在相同key的缓存元素，如果存在就不再执行该方法，而是直接从缓存中获取结果进行返回，否则才会执行并将返回结果存入指定的缓存中。
    //* @CacheEvict : 清除缓存。用在update，delete方法上。update也可以用@CachePut
    //* @CachePut : @CachePut也可以声明一个方法支持缓存功能。使用@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。

    @Cacheable(value = "tttt", key = "'users_'+#id")
    public Person getPerson(int id) {
        System.out.println("未从缓存读取 " + id);
        switch (id) {
            case 1:
                return p1;
            case 2:
                return p2;
            default:
                return p3;
        }
    }

    //设置缓存key为 tttt::users_1 value为 方法的返回值
    @CacheEvict(value = "tttt", key = "'users_'+#id")
    public void updatePerson(int id) {
        System.out.println("更新缓存 " + id);
    }

    @CacheEvict(value = "tttt", key = "'users_'+#id")
    public void delete(int id) {
        System.out.println("删除缓存 " + id);
    }

    /** 通过方法获取key */
    @Cacheable(value = "materialtypetree", key = "#root.target.getTenantId()+'materialtypetree'")
    public void getList(){

    }
    public String getTenantId(){
        return "ABC123";
    }

    /**
     * 在spring cache中，@CacheEvict是清除缓存的注解。其中注解参数可以只有value,key意思是清除在value值空间中的key值数据，此时默认在当前注解方法成功执行之后再清除。
     * 这时候就会存在一个问题，也许你的注解方法成功执行了删除操作，但是后续代码抛出异常导致未能清除缓存，下次查询时依旧从缓存中去读取，这时查询到的结果值是删除操作之前的值。
     * 有一个简单的解决办法，在注解参数里面加上beforeInvocation为true，意思是说当执行这个方法之前执行清除缓存的操作，这样不管这个方法执行成功与否，该缓存都将不存在。
     * 当注解参数加上allEntries为true时，意思是说这个清除缓存是清除当前value值空间下的所有缓存数据。
     * @param record
     * @return
     */
    @CacheEvict(value = "materialtypetree", key = "#root.target.getTenantId()+'materialtypetree'", beforeInvocation=true)
    public int updateByPrimaryKeySelective(Object record) {
        return 1;
    }

}
