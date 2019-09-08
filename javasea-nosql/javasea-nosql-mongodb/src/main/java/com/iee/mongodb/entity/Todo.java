package com.iee.mongodb.entity;

import org.springframework.data.annotation.Id;

//@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
//@Document
//@Data
public class Todo {
    @Id
    String id;
    String name;
    int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
