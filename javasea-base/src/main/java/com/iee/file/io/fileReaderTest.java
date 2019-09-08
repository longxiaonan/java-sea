package com.iee.file.io;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @ClassName fileReader
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/7/29 15:49
 */
public class fileReaderTest {
    public static void main(String[] args) {
//        test1();
        test2();
    }

    private static void test2() {
        try {
            BufferedReader br = Files.newBufferedReader(Paths.get("conf/aa.properties"), Charset.forName("utf8"));
            while(true){
                String line = br.readLine();
                if(line == null){
                    break;
                }
                System.out.println(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void test1() {
        try {
//            BufferedReader reader = new BufferedReader(new FileReader("classpath:conf/bb.ini"));//加载不到classpath
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource resource = resolver.getResource("classpath:conf/bb.ini");
            //转换成reader
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = null;

            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
