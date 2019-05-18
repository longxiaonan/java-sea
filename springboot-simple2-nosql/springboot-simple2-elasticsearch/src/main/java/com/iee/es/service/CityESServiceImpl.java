package com.iee.es.service;

import com.iee.es.dao.CityRepository;
import com.iee.es.entity.City;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 城市 ES 业务逻辑实现类
 *
 * Created by bysocket on 07/02/2017.
 */
@Service
public class CityESServiceImpl implements CityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityESServiceImpl.class);

    @Autowired
    CityRepository cityRepository;

    /**用 Postman 工具新增两个城市 新增城市信息
     * POST http://127.0.0.1:8080/api/city
     * {
     * "id":"1",
     * "provinceid":"1",
     * "cityname":"温岭",
     * "description":"温岭是个好城市"
     * }
     *
     * POST http://127.0.0.1:8080/api/city
     * {
     * "id":"2",
     * "provinceid":"2",
     * "cityname":"温州",
     * "description":"温州是个热城市"
     * }
     **/
    @Override
    public Long saveCity(City city) {

        City cityResult = cityRepository.save(city);
        return cityResult.getId();
    }

    /**
     * 为什么会出现 温州 城市呢？因为function score query 权重分查询，无相关的数据默认分值为 1。如果想除去，设置一个 setMinScore 分值即可。
     **/
    @Override
    public List<City> searchCity(Integer pageNumber,
                                 Integer pageSize,
                                 String searchContent) {
//        // 分页参数
//        Pageable pageable = new PageRequest(pageNumber, pageSize);
//
//        // Function Score Query
//        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
//                .add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("cityname", searchContent)),
//                    ScoreFunctionBuilders.weightFactorFunction(1000))
//                .add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("description", searchContent)),
//                        ScoreFunctionBuilders.weightFactorFunction(100));
//
//        // 创建搜索 DSL 查询
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withPageable(pageable)
//                .withQuery(functionScoreQueryBuilder).build();
//
//        LOGGER.info("\n searchCity(): searchContent [" + searchContent + "] \n DSL  = \n " + searchQuery.getQuery().toString());
//
//        Page<City> searchPageResults = cityRepository.search(searchQuery);
//        return searchPageResults.getContent();
        return null;
    }

}