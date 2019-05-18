package com.iee.es.controller;

import com.iee.es.entity.City;
import com.iee.es.service.CityESServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName CityController
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/9/13 0013 19:39
 */
@RestController
public class CityController {

    @Autowired
    CityESServiceImpl cityESService;

    @PostMapping("/api/city")
    public  void  saveCity(@RequestBody City city){

        cityESService.saveCity(city);
    }

}
