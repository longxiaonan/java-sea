package com.iee.file.io;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

public class ReadFileTest {
	public static void main(String[] args) throws IOException {
		ResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
		Resource[] rs = resourceLoader.getResources("file:conf/*.properties");
		for (Resource resource : rs) {
			System.out.println(resource);
		}
	}
}
