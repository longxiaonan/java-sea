package com.javasea.easyexcel.relase;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/file/excel/")
public class DownTemplateController {

    @Value("${file.template}")
    private String templatePath;
    /**
     * 下载模版，通过指定文件名和模板名称进行下载模板
     * @param newName 重新设置下载后的文件名
     * @param templateName 模块对应的模板文件名
     * @param response
     */
    @GetMapping("/downTemple")
    public void downTempleFile(@RequestParam String templateName, @RequestParam String newName, HttpServletResponse response) {
        EasyExcelUtils.downTemplate(templateName, newName, templatePath, response);
    }

}
