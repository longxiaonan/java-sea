package com.iee.file.upload.controller;

import org.apache.commons.compress.utils.Lists;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description 通过getBytes的字节流写入到目标目录
 * @Author longxiaonan@163.com
 * @Date 23:05 2019/6/26 0026
 **/
@Controller
public class FileUploadController {

    @Value("${filePath}")
    private String filePath;

    @Value("${filePathTemp}")
    private String filePathTemp;

    @GetMapping("/upload")
    public String uploading() {
        //跳转到 templates 目录下的 uploading.html
        return "upload";
    }

    //处理文件上传
    @PostMapping("/uploading")
    public @ResponseBody
    String uploading(@RequestParam("file") MultipartFile file,
                     HttpServletRequest request, HttpServletResponse response) {
        try {
            // 读取文件
            List<String> strings = ExcelUtils.readExcel(file);
            List<String> collect = strings.stream().filter(a -> !Objects.isNull(a) && !"null".equals(a))
                    .collect(Collectors.toList());
            // 筛选出符合表达式的数据
            List<String> lists = handleData(collect);
            // 转换为txt文件
            long filePrefix = write2Txt(lists);
            downFile(getFileName(filePrefix), response);
            //上传保存到本地
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件上传失败!");
            return "文件上传处理失败";
        }
        return "文件上传处理成功";
    }

    private List<String> handleData(List<String> collect) {
        List<String> reg = collect.stream().map(a -> a.replace("、", " ")
                .replace("/", " ")
                .replace("（", " ")
                .replace("）", " ")
                .replace("(", " ")
                .replace(")", " ")
                .replaceAll("[A-Za-z0-9]*[\u4e00-\u9fa5][A-Za-z0-9]*", " ")
                .replaceAll("\\*[0-9]+", " ")
                .replaceAll(" +[0-9]+", " ")
                .replaceAll("\\[[0-9]+\\+[0-9]+\\]", "") // 匹配   [23+232]
                .trim()).filter(a ->
                a.matches("[A-Z][0-9]+-[0-9]*") // A10-11
                        || a.matches("[A-Z][0-9]+") // A10
                        || a.matches("([A-Z][A-Z][A-Z][0-9][0-9][0-9]([0-9]*[A-Z]*)* *)+") // PTC123PT23PT23
                        || a.matches("[A-Z][0-9]+-[0-9]+ ([A-Z][A-Z][A-Z][0-9][0-9][0-9]([0-9]*[A-Z]*)* *)*")
                        || a.matches("[A-Z][0-9]+ ([A-Z][A-Z][A-Z][0-9][0-9][0-9]([0-9]*[A-Z]*)* *)*")
        ).collect(Collectors.toList());
        // 对不符合条件的数据进行空格替换
        for (int i = 0; i < reg.size() - 1; i++) {
            String first = reg.get(i);
            String second = reg.get(i + 1);
            Boolean firstFlag = first.trim().matches("[A-Z][0-9]+-*[0-9]*");
            Boolean secondFlag = second.trim().matches("([A-Z][A-Z][A-Z][0-9][0-9][0-9]([0-9]*[A-Z]*)* *)+");
            if (firstFlag) { //A-10
                if (!secondFlag) { // A10 、A10 GFD1232
                    reg.set(i, " ");
                } else {
                    // 仓位和规格合并同一行
                    reg.set(i, first + " " + second);
                    reg.set(i + 1, " ");
                }
            }
        }
        // 去空
        reg = reg.stream().filter(a -> !Objects.isNull(a) && !"null".equals(a) && !" ".equals(a)).collect(Collectors.toList());
        // 仓位和规格合并同一行
        for (int i = 0; i < reg.size() - 1; i++) {
            String first = reg.get(i);
            String second = reg.get(i + 1);
            if (second.trim().matches("([A-Z][A-Z][A-Z][0-9][0-9][0-9]([0-9]*[A-Z]*)* *)+")) {
                if (first.trim().matches("[A-Z][0-9]+-*[0-9]* *([A-Z][A-Z][A-Z][0-9][0-9][0-9]([0-9]*[A-Z]*)* *)+")) {
                    reg.set(i, first + " " + second);
                    reg.set(i + 1, " ");
                }
            }
        }
        reg = reg.stream().filter(a -> !Objects.isNull(a) && !"null".equals(a) && !" ".equals(a)).map(a -> a.replaceAll(" +", "\\|")).collect(Collectors.toList());
        List<String> lists = Lists.newArrayList();
        reg.forEach(a -> {
            String[] strArr = a.split("\\|");
            for (int i = 1; i < strArr.length; i++) {
                if (strArr.length > 1) {
                    lists.add(strArr[0] + " " + strArr[i]);
                }
            }
        });
        return lists;
    }

    private long write2Txt(List<String> strings) throws Exception {
        long currTime = System.currentTimeMillis();
        BufferedWriter bw = new BufferedWriter(new FileWriter(getFileName(currTime)));
        for (String line : strings) {
            bw.write(line);
            bw.newLine();
        }
        bw.flush();
        bw.close();
        return currTime;
    }

    private String getFileName(long currTime) {
        return filePathTemp + currTime + ".txt";
    }

    private void downFile(String sourceFileName, HttpServletResponse response) {
        try(InputStream inputStream = new FileInputStream(new File(sourceFileName));
            OutputStream outputStream = response.getOutputStream()) {
            response.reset();
            //设置ContentType字段值“text/html”，“application/json”
            response.setContentType("application/x-download;charset=UTF-8");
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + "仓位转换"+ System.currentTimeMillis() + ".txt");
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
