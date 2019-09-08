package com.iee.spring;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 实现在spring.xml加载时只需Listener
 * @author longxn
 *
 */
public class TestApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
	  @Override
	    public void onApplicationEvent(ContextRefreshedEvent event) {
		  	System.out.println(">>>>>>>>>>>11111111111111");
	        if(event.getApplicationContext().getParent() == null){//root application context 没有parent
	            System.out.println(">>>>>>>>>>>>>>>22222222222");
	        }
	    }
}
