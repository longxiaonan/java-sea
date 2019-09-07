package com.iee.es.dao;

import com.iee.es.entity.City;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends ElasticsearchRepository<City,Long> {


}