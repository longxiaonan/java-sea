package com.iee.es.dao;

import com.iee.es.entity.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookDao extends ElasticsearchRepository<Book,String>{

}