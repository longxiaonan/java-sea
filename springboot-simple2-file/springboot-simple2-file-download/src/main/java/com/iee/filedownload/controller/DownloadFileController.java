package com.iee.filedownload.controller;

import com.iee.common.utils.FileHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
 * @ClassName Controller
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/3/18 0018 19:44
 */
@RestController
@Slf4j
@Api(value = "下载模板", tags = { "" })
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
    @ApiOperation(value = "下载模版", notes = "下载模版")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "branchId", value = "网点ID", dataType = "String", required = true) })
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
    @GetMapping("/downTempleFileCommon2")
    @ApiOperation(value = "下载模版", notes = "下载模版")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "filename", value = "本次下载的文件名", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "query", name = "partname", value = "模块对应的模板文件名", dataType = "String", required = true)})
    public void downTempleFileCommon2(@RequestParam String filename, @RequestParam String partname, HttpServletRequest request, HttpServletResponse response) {
        try(InputStream inputStream = new FileInputStream(new File(templatePath,partname + ".xlsx"));
            OutputStream outputStream = response.getOutputStream()) {
            response.reset();
            //设置ContentType字段值“text/html”，“application/json”
            response.setContentType("application/x-download;charset=UTF-8");

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

            response.addHeader("Content-type", "application/octet-stream;charset=UTF-8");
            log.info("-------------downTempleFileCommon path----------" + templatePath + partname + ".xlsx");
            //filename:重新设置下载后的文件名
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + filename + ".xlsx");
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
