package com.javasea.orm.springdata.multidatasource.entity.primary;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author F嘉阳
 * @date 2019-03-30 9:11
 */
@Data
@Entity
public class Product {

    @Id
    private Integer id;

    private String name;

    private Integer price;
}
