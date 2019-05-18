package com.iee.file.archive.simple;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SaveToLocal
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/10/30 0030 10:48
 */
public class SaveToLocal {
    public static void main(String[] args) {
        //files();
        //fileUtils();
    }

    private static void fileUtils() {
        try {
            File file = new File("SaveToLocal.txt");
            List<String> list = new ArrayList<>();
            list.add("aa");
            list.add("bb");
            list.add("cc");
            FileUtils.writeLines(file, list, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void files() {
        String msg = "hello";
        try {
            Files.write(Paths.get("./duke.txt"), msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
