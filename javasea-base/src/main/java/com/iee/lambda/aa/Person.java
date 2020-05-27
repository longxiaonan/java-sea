package com.iee.lambda.aa;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Person {

    public enum Sex {
        MALE, FEMALE
    }

    String name;
    Integer age;
    LocalDate birthday;
    Sex gender;
    String emailAddress;

    public String printPerson() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
