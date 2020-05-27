package com.javasea.file.preview.controller;


import com.google.common.io.Resources;
import com.javasea.file.preview.utils.FileUtil;
import com.javasea.file.preview.utils.Office2PDF;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * @Description 文件预览测试类
 * @Author longxiaonan@163.com
 * @Date 11:39 2019/10/31 0031
 **/
@ComponentScan
@Controller
@RequestMapping("/test")
public class ReadAndDownLoadController {
    /**
     * base路径
     */
    private final String BASE_PATH = "doc/";

    /**
     * @return choose页面
     */
    @RequestMapping("/choose")
    public String chooseFile() {
        return "ShowChoose";
    }

    /**
     * 如果把代码整合放到SSM项目中去，小数点后面的数据将直接被忽略，也就是访问/read/java.doc，
     * 理想情况是接收到了fileName这个参数为 "java.doc"，结果Debug发现fileName 是"java" ，后面的.doc不见了。
     * 解决方案就是在@RequestMapping的value中使用SpEL来表示，value中的{fileName}换成{fileName:.+}
     *
     * @param res      响应对象
     * @param fileName 请求预览文件名
     * @throws Exception
     */
    @RequestMapping("/read/{fileName}")
    public void readFile(HttpServletResponse res, @PathVariable String fileName) throws Exception {
        //判断是pdf还是word还是excel
        //若是pdf直接读 否则转pdf 再读
        String filePath = fileHandler(fileName);
        try (InputStream in = new FileInputStream(filePath); OutputStream out = res.getOutputStream();) {
            res.setContentType("application/pdf");

            byte[] b = new byte[1024];
            int len = 0;
            while ((len = in.read(b)) != -1) {
                out.write(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件处理
     *
     * @param fileName
     * @return
     */
    private String fileHandler(String fileName) throws IllegalArgumentException {
        String fileSuffix = FileUtil.getFileSuffix(fileName);
        System.out.println(fileSuffix);
        //读取不到文件抛异常 IllegalArgumentException
        URL resource = Resources.getResource(BASE_PATH + fileName);
        String path = resource.getPath();
        System.out.println("path:" + path);
        if ("pdf".equals(fileSuffix)) {
            return path;
        } else {
            return Office2PDF.openOfficeToPDF(path).getAbsolutePath();
        }
    }
}
