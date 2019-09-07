package com.iee.webbase.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iee.webbase.common.ResultBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

/**
 * modelConvertDemo 层单测
 *
 * @author dadiyang
 * @since 2019-06-02
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountControllerTest {
    private static final ObjectMapper JACKSON_MAPPER = new ObjectMapper();

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void getAccount() throws Exception {
        // 参数校验不通过
        mockMvc.perform(get("/account/{id}", -1))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("msg", "主键必须大于0"));

        // 正确查询到结果
        Account account = new Account(1, "张三", 12.0);
        mockMvc.perform(get("/account/{id}", 1))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(JACKSON_MAPPER.writeValueAsString(ResultBean.success(account))));
    }

    @Test
    public void add() throws Exception {
        Account account = new Account();
        // 参数校验不通过
        mockMvc.perform(post("/account/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(JACKSON_MAPPER.writeValueAsString(account)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("msg", "姓名不能为空"));

        // 正确添加并返回
        account = new Account("尼古拉斯", 10000.0);
        mockMvc.perform(post("/account/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(JACKSON_MAPPER.writeValueAsString(account)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(JACKSON_MAPPER.writeValueAsString(ResultBean.success(11))));
    }
}
