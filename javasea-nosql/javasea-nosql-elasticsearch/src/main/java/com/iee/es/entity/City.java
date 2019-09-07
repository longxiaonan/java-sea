package com.iee.es.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * 注意 index 配置必须是全部小写，不然会暴异常。 org.elasticsearch.indices.InvalidIndexNameException: Invalid index name [cityIndex], must be lowercase
 */
@Document(indexName = "cityindex", type = "city")
@Data
public class City implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 城市编号
     */
    private Long id;

    /**
     * 省份编号
     */
    private Long provinceid;

    /**
     * 城市名称
     */
    private String cityname;

    /**
     * 描述
     */
    private String description;
}