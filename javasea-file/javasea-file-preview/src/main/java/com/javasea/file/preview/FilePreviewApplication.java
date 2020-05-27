package com.javasea.file.preview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author longxiaonan@aliyun.com
 */
@SpringBootApplication
public class FilePreviewApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(FilePreviewApplication.class, args);
	}
}
