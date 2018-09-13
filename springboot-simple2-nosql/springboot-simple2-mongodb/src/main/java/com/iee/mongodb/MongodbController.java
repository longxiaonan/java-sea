package com.iee.mongodb;

import com.iee.mongodb.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: TestController
 * @Author: huangzf
 * @Date: 2018/7/30 15:38
 * @Description:
 */
@RequestMapping("/test")
@RestController
public class MongodbController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("get")
    public List<User> getList(){
        final Pageable pageableRequest = new PageRequest(0, 100);
        Query query = new Query();
        query.with(pageableRequest);
        Sort sort = new Sort(Direction.ASC,"dataTime");
        query.with(sort);
        List<User> list = mongoTemplate.find(query,User.class);
        return list;
    }

}
