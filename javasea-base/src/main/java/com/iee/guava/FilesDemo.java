package com.iee.guava;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

/**
 * 以前我们写文件读取的时候要定义缓冲区，各种条件判断，各种$%#$@# 而现在我们只需要使用好guava的api
 * 就能使代码变得简洁，并且不用担心因为写错逻辑而背锅了
 *
 * @author longxn
 *
 */
public class FilesDemo {
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		/** 谷歌的Files可以将配置文件读取的一个List中 */
		File file = new File("test.txt");
		List<String> list = null;
		List<String> readAllLines = java.nio.file.Files.readAllLines(Paths.get("test1.txt"));
		System.out.println(readAllLines);

		try {
			list = Files.readLines(file, Charsets.UTF_8);
			System.out.println(list);//[aa=ttttttttttttwwwwwwwwwwwwerrrrrr, bb=fsdfs, sdf, sd, wgweg, we, y, we, yw, ]
		} catch (Exception e) {
		}
		// Files.copy(file,to); //复制文件
		// Files.deleteDirectoryContents(File directory); //删除文件夹下的内容(包括文件与子文件夹)
		// Files.deleteRecursively(File file); //删除文件或者文件夹
		// Files.move(File from, File to); //移动文件
		URL url = Resources.getResource("abc.xml"); // 获取classpath根下的abc.xml文件url
		System.out.println(url);//file:/C:/vtx/workspace/123/target/classes/abc.xml

		/** resourceLoader
		 * 可以实现通过resourceLoader批量读取properties到项目中
		 * getResource方法 默认加载的是classpath下, 如果加file, 配置成相对路径 , 表示加载的是项目下 . 可以使用通配的方式进行读取 */
		ResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
		Resource path = resourceLoader.getResource("test1.txt");
		System.out.println("path:"+path);//path:class path resource [test1.txt]
//		Resource[] resource = resourceLoader.getResources("*.txt");//file:/C:/vtx/workspace/123/target/classes/
//		Resource[] resource = resourceLoader.getResources("classpath:*txt");//效果同上
//		Resource[] resource = resourceLoader.getResources("classpath:/*txt");//效果同上
		///如果是web项目,加载的是C:\vtx\soft\eclipse\,linux不是环境下,目录应该是tomcat/bin下, 或者是tomcat/conf下
		Resource[] resource = resourceLoader.getResources("file:conf/mongo.properties");//项目目录下,和sr同级目录
//		Resource[] resource = resourceLoader.getResources("file:/conf/mongo.properties");//不知道是哪, 错误
		Properties properties = new Properties();
		for (Resource r : resource) {
			System.out.println(r.getURI());//  file:/C:/vtx/workspace/123/target/classes/test2.txt
			System.out.println(r.getURL());//  file:/C:/vtx/workspace/123/target/classes/test2.txt
			System.out.println(r.getURI().getPath());//  /C:/vtx/workspace/123/target/classes/test2.txt
			properties.load(new InputStreamReader(r.getInputStream(), "utf-8"));
		}
		String property1 = properties.getProperty("aa");
		String property2 = properties.getProperty("HOST");
		System.out.println(property1);
		System.out.println(property2);//192.168.3.206

	}

}
