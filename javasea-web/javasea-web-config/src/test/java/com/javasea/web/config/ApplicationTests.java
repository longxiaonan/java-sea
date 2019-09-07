package com.javasea.web.config;

import com.javasea.web.config.config.outside.DBConfig1;
import com.javasea.web.config.config.outside.DBConfig2;
import com.javasea.web.config.config.InfoConfig1;
import com.javasea.web.config.config.InfoConfig2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {
        APPlication.class,
})
public class ApplicationTests {

    @Autowired
    DBConfig2 dbConfig2;

    @Autowired
    DBConfig1 dbConfig1;

    @Autowired
    InfoConfig1 infoConfig1;

    @Autowired
    InfoConfig2 infoConfig2;

    @Test
    public void test(){
        System.out.println(dbConfig1);
        System.out.println(dbConfig2);
        System.out.println(infoConfig1);
        System.out.println(infoConfig2);
    }
}
