package com.iee.template.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @ClassName com.iee.templates.modelConvertDemo.ControllerTest
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/8/12 19:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ControllerTest.class)
public class ControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    //在每个方法执行前执行
    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenUploadSuccess() throws Exception{
        String result = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/file-upload")
                .file(new MockMultipartFile("file", "test.txt", "multipart/form-data", "hello file upload".getBytes("UTF-8"))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

}
