package com.javasea.web.junit.mode2;

import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        Application.class,
})
public class RestAssuredTestDemonstration {

//    @Autowired
//    private TopTalentController topTalentController;
//
//    @Test
//    public void shouldGetMaryAndJoel() throws Exception {
//        // given
//        MockMvcRequestSpecification givenRestAssuredSpecification = RestAssuredMockMvc.given()
//                .standaloneSetup(topTalentController);
//
//        // when
//        MockMvcResponse response = givenRestAssuredSpecification.when().get("/toptal/get");
//
//        // then
//        response.then().statusCode(200);
//        response.then().body("name", hasItems("Mary", "Joel"));
//    }

}
