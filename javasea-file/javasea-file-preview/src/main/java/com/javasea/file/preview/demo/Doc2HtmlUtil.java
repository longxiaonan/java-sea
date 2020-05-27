package com.javasea.file.preview.demo;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;

import java.io.*;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Doc2HtmlUtil {

    private static Doc2HtmlUtil doc2HtmlUtil;

    /**
     * 获取Doc2HtmlUtil实例
     */
    public static synchronized Doc2HtmlUtil getDoc2HtmlUtilInstance() {
        if (doc2HtmlUtil == null) {
            doc2HtmlUtil = new Doc2HtmlUtil();
        }
        return doc2HtmlUtil;
    }

    /**
     * 转换文件成html
     *
     * @param fromFileInputStream:
     * @throws IOException
     */
    public String file2Html(InputStream fromFileInputStream, String toFilePath, String type) throws IOException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timesuffix = sdf.format(date);
        String docFileName = null;
        String htmFileName = null;
        if("doc".equals(type)){
            docFileName = "doc_" + timesuffix + ".doc";
            htmFileName = "doc_" + timesuffix + ".html";
        }else if("docx".equals(type)){
            docFileName = "docx_" + timesuffix + ".docx";
            htmFileName = "docx_" + timesuffix + ".html";
        }else if("xls".equals(type)){
            docFileName = "xls_" + timesuffix + ".xls";
            htmFileName = "xls_" + timesuffix + ".html";
        }else if("ppt".equals(type)){
            docFileName = "ppt_" + timesuffix + ".ppt";
            htmFileName = "ppt_" + timesuffix + ".html";
        }else{
            return null;
        }

        File htmlOutputFile = new File(toFilePath + File.separatorChar + htmFileName);
        File docInputFile = new File(toFilePath + File.separatorChar + docFileName);
        if (htmlOutputFile.exists())
            htmlOutputFile.delete();
        htmlOutputFile.createNewFile();
        if (docInputFile.exists())
            docInputFile.delete();
        docInputFile.createNewFile();
        /**
         * 由fromFileInputStream构建输入文件
         */
        try {
            OutputStream os = new FileOutputStream(docInputFile);
            int bytesRead = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((bytesRead = fromFileInputStream.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            os.close();
            fromFileInputStream.close();
        } catch (IOException e) {
        }

        OpenOfficeConnection connection = new SocketOpenOfficeConnection("192.168.1.234",8100);
        try {
            connection.connect();
        } catch (ConnectException e) {
            System.err.println("文件转换出错，请检查OpenOffice服务是否启动。");
        }
        // convert
        DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
        converter.convert(docInputFile, htmlOutputFile);
        connection.disconnect();
        // 转换完之后删除word文件
//        docInputFile.delete();
        return htmFileName;
    }

    /**
     * 转换文件成pdf
     *
     * @param fromFileInputStream:
     * @throws IOException
     */
    public String file2pdf(InputStream fromFileInputStream, String toFilePath,String type) throws IOException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timesuffix = sdf.format(date);
        String docFileName = null;
        String htmFileName = null;
        if("doc".equals(type)){
            docFileName = "doc_" + timesuffix + ".doc";
            htmFileName = "doc_" + timesuffix + ".pdf";
        }else if("docx".equals(type)){
            docFileName = "docx_" + timesuffix + ".docx";
            htmFileName = "docx_" + timesuffix + ".pdf";
        }else if("xls".equals(type)){
            docFileName = "xls_" + timesuffix + ".xls";
            htmFileName = "xls_" + timesuffix + ".pdf";
        }else if("ppt".equals(type)){
            docFileName = "ppt_" + timesuffix + ".ppt";
            htmFileName = "ppt_" + timesuffix + ".pdf";
        }else{
            return null;
        }

        File htmlOutputFile = new File(toFilePath + File.separatorChar + htmFileName);
        File docInputFile = new File(toFilePath + File.separatorChar + docFileName);
        if (htmlOutputFile.exists())
            htmlOutputFile.delete();
        htmlOutputFile.createNewFile();
        if (docInputFile.exists())
            docInputFile.delete();
        docInputFile.createNewFile();
        /**
         * 由fromFileInputStream构建输入文件
         */
        try {
            OutputStream os = new FileOutputStream(docInputFile);
            int bytesRead = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((bytesRead = fromFileInputStream.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            os.close();
            fromFileInputStream.close();
        } catch (IOException e) {
        }

        OpenOfficeConnection connection = new SocketOpenOfficeConnection("192.168.1.234",8100);
        try {
            connection.connect();
        } catch (ConnectException e) {
            System.err.println("文件转换出错，请检查OpenOffice服务是否启动。");
        }
        // convert
        DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
        converter.convert(docInputFile, htmlOutputFile);
        connection.disconnect();
        // 转换完之后删除word文件
        docInputFile.delete();
        return htmFileName;
    }

    public static void main(String[] args) throws IOException {
        Doc2HtmlUtil coc2HtmlUtil = getDoc2HtmlUtilInstance();
        File file = null;
        FileInputStream fileInputStream = null;


        file = new File("E:/project/java-sea/javasea-file/javasea-file-preview/src/main/resources/doc/a.doc");
        fileInputStream = new FileInputStream(file);
//        coc2HtmlUtil.file2pdf(fileInputStream, "F:/test","xls");
        coc2HtmlUtil.file2Html(fileInputStream, "F:/test","doc");
       coc2HtmlUtil.file2pdf(fileInputStream, "F:/test","doc");

  /*      file = new File("D:/poi-test/周报模版.ppt");
        fileInputStream = new FileInputStream(file);
//      coc2HtmlUtil.file2Html(fileInputStream, "D:/poi-test/openOffice/ppt","ppt");
        coc2HtmlUtil.file2pdf(fileInputStream, "D:/poi-test/openOffice/ppt","ppt");

        file = new File("D:/poi-test/test.docx");
        fileInputStream = new FileInputStream(file);
//      coc2HtmlUtil.file2Html(fileInputStream, "D:/poi-test/openOffice/docx","docx");
        coc2HtmlUtil.file2pdf(fileInputStream, "D:/poi-test/openOffice/docx","docx");*/

    }

}
