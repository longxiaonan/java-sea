package com.javasea.nosql.zookeeper.zookeeper.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RequestMapping("/test")
@RestController
public class TestController {

    @Autowired
    public ServiceRegistry serviceRegistry;

    @RequestMapping(name="HelloService",method = RequestMethod.GET,path = "/hello")
    public String hello(){
        return "Hello";
    }

    @RequestMapping(value="/world", method=RequestMethod.GET)
    public String world(){
        System.out.println("11111");
      ArrayList<String> arrays = serviceRegistry.getValue("/registry/HelloService");
      return arrays.toString();
    }
}
