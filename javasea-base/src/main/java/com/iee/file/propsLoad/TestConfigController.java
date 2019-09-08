package com.iee.file.propsLoad;

import com.iee.common.utils.SpringContextUtils;
import com.iee.file.propsLoad.array.ArrayProps;
import com.iee.file.propsLoad.array.ListProps;
import com.iee.file.propsLoad.innerClass.ScProps;
import com.iee.file.propsLoad.other.map.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName Test
 * @Description TODO
 * @Author longxn
 * @Date 2018/7/6 19:32
 */
@RestController
public class TestConfigController {

    AppProperties appProperties1 ;
    AppProperties appProperties2 ;

    @Autowired
    ScProps scProps;

    @Autowired
    ArrayProps arrayProps;

    @Autowired
    ListProps listProps;

    @Autowired
    public TestConfigController() {
        this.appProperties1 = SpringContextUtils.getBean("aaProp",AppProperties.class);
        this.appProperties2 = SpringContextUtils.getBean("bbProp",AppProperties.class);
    }

    @GetMapping("testConfig")
    public void test(){
        System.out.println(appProperties1.getProperty("aa"));
        System.out.println(appProperties1.getProperty("bb"));
        System.out.println(appProperties2.getProperty("aa"));
        System.out.println(appProperties2.getProperty("bb"));
    }

    @GetMapping("testSc")
    public void test1(){
        System.out.println(scProps.getDespatch().getThreadNum());
        System.out.println(scProps.getDevconnect().getAgentUrl());
        System.out.println(scProps.getDevconnect().getCmdUrl());
        System.out.println(scProps.getTransparentTransfer().getTthost());
        System.out.println(scProps.getTransparentTransfer().getIds());
    }

    @GetMapping("testArray")
    public void test2(){
        System.out.println(arrayProps.getArray());
    }

    @GetMapping("testList")
    public void test3(){
        listProps.getArray();
    }
}
