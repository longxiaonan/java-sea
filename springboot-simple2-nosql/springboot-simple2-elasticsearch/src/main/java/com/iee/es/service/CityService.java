package com.iee.es.service;

import com.iee.es.entity.City;

import java.util.List;

/**
 * @ClassName CityService
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/9/13 0013 16:46
 */
public interface CityService {

    Long saveCity(City city);

    List<City> searchCity(Integer pageNumber,
                                 Integer pageSize,
                                 String searchContent);
}
