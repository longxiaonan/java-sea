package com.javasea.file.preview.demo;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;

import java.io.File;

/**
 * @ClassName Demo2
 * @Description https://www.jianshu.com/p/348c0eb277f6
 * @Author longxiaonan@163.com
 * @Date 2019/10/31 0031 15:04
 */
public class Demo2 {

    public static void main(String[] args) throws Exception {
        convert("E:\\project\\java-sea\\javasea-file\\javasea-file-preview\\src\\main\\resources\\doc\\a.doc",
                "F:\\test\\a.pdf");
    }

    /**
     * 将word文档转换成html文档
     *
     * @param docFilePath  需要转换的word文档
     * @param filepath 转换之后html的存放路径
     * @return 转换之后的html文件
     */
    public static void convert(String docFilePath, String filepath) throws Exception {
        File docFile = new File(docFilePath);
        // 创建保存html的文件
        File htmlFile = new File(filepath);
        // 创建Openoffice连接,指定服务ip 端口
        OpenOfficeConnection con = new SocketOpenOfficeConnection("127.0.0.1", 8100);
        // 连接
        con.connect();
        System.out.println("获取链接成功！！！！！！！！！！！！！！！！！！");
        // 创建转换器
        DocumentConverter converter;
        // 转换文档问html
        try {
            converter = new StreamOpenOfficeDocumentConverter(con);
            converter.convert(docFile, htmlFile);
            System.out.println("获取链接成功！！OpenOfficeDocumentConverter");
        } catch (Exception e) {
            converter = new OpenOfficeDocumentConverter(con);
            converter.convert(docFile, htmlFile);
            System.out.println("获取链接成功！！StreamOpenOfficeDocumentConverter");
            e.printStackTrace();
        } finally {
            // 关闭openoffice连接
            con.disconnect();
        }
        System.out.println("关闭连接！！");
    }
}
