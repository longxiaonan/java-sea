package com.iee.lambda.aa;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @ClassName Demo
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2020/4/15 0015 21:30
 */
public class Demo {
    public static void main(String[] args) {
        List<Person> roster = Lists.newArrayList();

        printPersonsWithPredicate(
                roster,
                p -> p.getGender() == Person.Sex.MALE
                        && p.getAge() >= 18
                        && p.getAge() <= 25
        );

        processPersons(
                roster,
                p -> p.getGender() == Person.Sex.MALE
                        && p.getAge() >= 18
                        && p.getAge() <= 25,
                p -> p.printPerson()
        );

        processPersonsWithFunction(
                roster,
                p -> p.getGender() == Person.Sex.MALE
                        && p.getAge() >= 18
                        && p.getAge() <= 25,
                p -> p.getAge(),
                email -> System.out.println(email)
        );

    }

    public static void printPersonsWithPredicate(
            List<Person> roster, Predicate<Person> tester) {
        for (Person p : roster) {
            if (tester.test(p)) {
                p.printPerson();
            }
        }
    }

    public static void processPersons(
            List<Person> roster,
            Predicate<Person> tester,
            Consumer<Person> block) {
        for (Person p : roster) {
            if (tester.test(p)) {
                block.accept(p);
            }
        }
    }

    public static void processPersonsWithFunction(
            List<Person> roster,
            Predicate<Person> tester,
            Function<Person, Integer> mapper,
            Consumer<Integer> block) {
        for (Person p : roster) {
            if (tester.test(p)) {
                Integer data = mapper.apply(p);
                block.accept(data);
            }
        }
    }



}
