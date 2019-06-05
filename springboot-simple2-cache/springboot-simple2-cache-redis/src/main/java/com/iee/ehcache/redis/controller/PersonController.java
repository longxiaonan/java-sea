package com.iee.ehcache.redis.controller;

import com.iee.ehcache.redis.entity.Person;
import com.iee.ehcache.redis.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 用于测试的controller 测试地址：
 * http://localhost:8080/person/get?id=1
 * */
@RequestMapping("/person")
@RestController
public class PersonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    @RequestMapping("/get")
    public Person getPerson(int id) {
        Person person = personService.getPerson(id);
        LOGGER.info("读取到数据 " + person.getFirstName() + "," + person.getLastName());
        return person;
    }

    @RequestMapping("/update")
    public void updatePerson(int id) {
        personService.updatePerson(id);
    }

    @RequestMapping("/delete")
    public void deletePerson(int id) {
        personService.delete(id);
    }
}
