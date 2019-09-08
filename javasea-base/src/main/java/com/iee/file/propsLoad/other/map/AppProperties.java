package com.iee.file.propsLoad.other.map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;

public class AppProperties extends PropertyPlaceholderConfigurer {

    private Properties properties;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        this.properties = props;
    }

    public static AppProperties getInst(String fileName){
        AppProperties appProperties = new AppProperties();
        appProperties.setLocation(new ClassPathResource(fileName));
        appProperties.setIgnoreUnresolvablePlaceholders(true);
        appProperties.setIgnoreResourceNotFound(true);
        return appProperties;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        if(null == properties.getProperty(key)){
        	return defaultValue;
        }
		return properties.getProperty(key);
    }

}
