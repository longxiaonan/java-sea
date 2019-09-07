package com.iee.es.dao;

import com.iee.es.entity.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.Repository;

public interface BookDao2 extends Repository<Book,String> {

}