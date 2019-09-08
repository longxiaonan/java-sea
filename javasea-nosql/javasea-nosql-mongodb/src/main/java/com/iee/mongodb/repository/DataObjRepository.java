package com.iee.mongodb.repository;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.mongodb.repository.MongoRepository;

//@RepositoryRestResource(collectionResourceRel = "dataObj", path = "dataObj")
public interface DataObjRepository extends MongoRepository<JSONObject, String>{

}
