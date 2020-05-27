package com.javasea.file.easyexcel.controller;

import com.javasea.file.easyexcel.utils.EasyPoiUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;



/**
 * @ClassName DownTemplateController
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2020/3/6 0006 13:02
 */
@Slf4j
@RestController
public class DownTemplateController {

    @Value("${file.template}")
    private String templatePath;
    /**
     * 下载模版，通过指定文件名和模板名称进行下载模板
     * @param newName 重新设置下载后的文件名
     * @param templateName 模块对应的模板文件名
     * @param request
     * @param response
     */
    @GetMapping("/downTemple")
    public void downTempleFile(@RequestParam String newName, @RequestParam String templateName,
                               HttpServletRequest request, HttpServletResponse response) {
        EasyPoiUtils.downExcel(templateName, newName, templatePath, response);
    }

}
