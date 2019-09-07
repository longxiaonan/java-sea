package com.javasea.orm.springdata.multidatasource.entity.backup;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author F嘉阳
 * @date 2019-03-30 9:36
 */
@Data
@Entity(name = "tb_order")  //不指定表名会报错
public class Order {

    @Id
    private Integer id;

    private String orderName;

    private Integer price;
}
