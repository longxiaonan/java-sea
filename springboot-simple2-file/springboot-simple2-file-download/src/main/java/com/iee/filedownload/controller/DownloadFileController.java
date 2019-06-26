package com.iee.filedownload.controller;

import com.iee.filedownload.common.FileHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @ClassName Controller
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/3/18 0018 19:44
 */
@RestController
@Slf4j
public class DownloadFileController {

    @Value("${local.folder}")
    private String folder = "";
    @Value("${local.filesize}")
    private int filesize; //文件大小上限
    @Value("${local.fixType}")
    private String fixType; //文件类型
    @Value("${local.templatefolder}")
    private String templatePath;

    /**
     * 传对应模块的模板文件，和想要的文件名
     *
     * @param request
     * @param response
     */
    @GetMapping("/downTempleFileCommon")
    public void downTempleFileCommon(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            //设置ContentType字段值
            //response.setContentType("application/x-download;charset=utf-8");
            //获取所要下载的文件名称
            String filename = request.getParameter("filename");
            String partname = request.getParameter("partname");//模块对应的模板文件名

            //通知浏览器以下载的方式打开
            response.addHeader("Content-type", "application/octet-stream");
//            response.addHeader("Content-type", "application/force-download");
            //通知文件流读取文件
            FileHelper.createFolder(templatePath);
            filename = templatePath + partname + ".xlsx";
            File file = new File(filename);
            String path = file.getAbsolutePath();
            log.info("-------------downTempleFileCommon path----------" + path);
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + filename + path.substring(path.lastIndexOf(".")));
            InputStream in = new FileInputStream(path);

            //获取response对象的输出流
            OutputStream out = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            //循环取出流中的数据
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 通过指定文件名和模板名称进行下载模板
     *
     * @param request
     * @param response
     */
    @GetMapping("/downTempleFileCommon2/{filename}/{rename}")
    public void downTempleFileCommon2(@PathVariable String filename, @PathVariable String rename, HttpServletRequest request, HttpServletResponse response) {
        try(InputStream inputStream = new FileInputStream(new File(templatePath,filename + ".xlsx"));
            OutputStream outputStream = response.getOutputStream()) {
            response.reset();
            //设置ContentType字段值“text/html”，“application/json”

            response.setContentType("application/x-download;charset=UTF-8");
            //服务端向客户端游览器发送文件时，如果是浏览器支持的文件类型，一般会默认使用浏览器打开，比如txt、jpg等，
            // 会直接在浏览器中显示，如果需要提示用户保存，就要利用Content-Disposition进行一下处理，
            // 关键在于一定要加上attachment：
            //Response.AppendHeader("Content-Disposition","attachment;filename=FileName.txt");
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + rename + ".xlsx");

            //通知浏览器以下载的方式打开
            /*********** Content-Type: application/octet-stream
             Content-Disposition: attachment; filename="picture.png"
             表示“我不清楚代码内容，请把其保存为一个文件，最好命名为picture.png”。

             Content-Type: image/png
             Content-Disposition: attachment; filename="picture.png"
             表示“这是一个PNG图像，请将其保存为一个文件，最好命名为picture.png”。

             Content-Type: image/png
             Content-Disposition: inline; filename="picture.png"
             表示“这是一个PNG图像，除非你不知道如何显示PNG图像，否则请显示它，如果用户选择保存它，我们建议文件名保存为picture.png”。
             ****/

            //content-type属性的"application/octet-stream"，这个属性值表示是任意的字节流，
            // 会强制浏览器打开save as对话框来保存文件，这个在你对mime类型并不了解时可以使用，
            // 这样就不用担心每种文件类型的mime类型是什么了.在使用这个属性值的时候，效果和使用content-disposition是一样的
//            response.addHeader("Content-type", "application/octet-stream;charset=UTF-8");

            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
