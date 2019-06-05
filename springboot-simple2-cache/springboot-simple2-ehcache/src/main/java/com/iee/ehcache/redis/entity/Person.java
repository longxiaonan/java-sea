package com.iee.ehcache.redis.entity;

import java.io.Serializable;

/** 因为要缓存，所以需要实现序列化接口 */
public class Person implements Serializable {
    private static final long serialVersionUID = -1L;
        private int id;
        private String firstName;
        private String lastName;

        public Person(int id, String firstName, String lastName) {
            this.id =id;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
}
