package com.iee.es.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 加上了@Document注解之后，默认情况下这个实体中所有的属性都会被建立索引、并且分词
 * 注意 index 配置必须是全部小写，不然会暴异常。 org.elasticsearch.indices.InvalidIndexNameException: Invalid index name [cityIndex], must be lowercase
 * @Document注解里面的几个属性，类比mysql的话是这样：
     index ==》索引 ==》Mysql中的一个库，库里面可以建立很多表，存储不同类型的数据，而表在ES中就是type。
　　 type ==》类型 ==》相当于Mysql中的一张表，存储json类型的数据
　　 document  ==》文档 ==》一个文档相当于Mysql一行的数据
　　 field ==》列 ==》相当于mysql中的列，也就是一个属性
 * @Id注解加上后，在Elasticsearch里相应于该列就是主键了，在查询时就可以直接用主键查询，后面一篇会讲到。其实和mysql非常类似，基本就是一个数据库。
 */
//http://127.0.0.1:9200/product
@Document(indexName = "product", type = "book")
@Data
public class Book {
    @Id
    String id;
    String name;
    String message;
    String type;
}