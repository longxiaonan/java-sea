package com.iee.es.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * String indexName();//索引库的名称，个人建议以项目的名称命名
 *
 * String type() default "";//类型，个人建议以实体的名称命名
 *
 * short shards() default 5;//默认分区数
 *
 * short replicas() default 1;//每个分区默认的备份数
 *
 * String refreshInterval() default "1s";//刷新间隔
 *
 * String indexStoreType() default "fs";//索引文件存储类型
 *
 * 加上了@Document注解之后，默认情况下这个实体中所有的属性都会被建立索引、并且分词。
 * 我们通过@Field注解来进行详细的指定，如果没有特殊需求，那么只需要添加@Document即可。
 *
 */
@Document(indexName="projectname",type="article",indexStoreType="fs",shards=5,replicas=1,refreshInterval="-1")
@Data
public class Article implements Serializable {
    @Id
    private Long id;
    /**标题*/
    private String title;
    /**摘要*/
    private String abstracts;
    /**内容*/
    private String content;
    /**发表时间*/
    private Date postTime;
    /**点击率*/
    private Long clickCount;

}