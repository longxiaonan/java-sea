package com.javasea.file.preview.artofsolving;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.servlet.http.HttpServletResponse;

import com.google.common.io.Resources;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 文件预览
 *
 * @author @Taven
 *
 */
@Controller
@RequestMapping("/test")
public class FilePreview {

    @RequestMapping("/pdf/{fileName}")
    public void pdf(@PathVariable String fileName, HttpServletResponse response) {
        // 调用转换类
        URL resource = Resources.getResource("doc/"+fileName);
        String path = resource.getPath();
        DocConverter docConverter = new DocConverter(path);
        try {
            docConverter.doc2pdf();
            File pdf = docConverter.getPdf();
            FileInputStream fileInputStream = new FileInputStream(pdf);
            response.reset();
            response.setHeader("Content-Disposition", "attachment;fileName=test.pdf");
            response.setContentType("multipart/form-data");
            OutputStream outputStream = response.getOutputStream();
            IOUtils.write(IOUtils.toByteArray(fileInputStream), outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/swf/{fileName}")
    public void swf(@PathVariable String fileName, HttpServletResponse response) throws Exception {
        // 调用转换类
        URL resource = Resources.getResource("doc/"+fileName);
        String path = resource.getPath();
        DocConverter docConverter = new DocConverter(path);
//        docConverter.conver();
        docConverter.pdf2swf();
        File pdf = docConverter.getPdf();
        try (FileInputStream fileInputStream = new FileInputStream(pdf)){
            response.reset();
            response.setHeader("Content-Disposition", "attachment;fileName=test.swf");
            response.setContentType("multipart/form-data");
            OutputStream outputStream = response.getOutputStream();
            IOUtils.write(IOUtils.toByteArray(fileInputStream), outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
