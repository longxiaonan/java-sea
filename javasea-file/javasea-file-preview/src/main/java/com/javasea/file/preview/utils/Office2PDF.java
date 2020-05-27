package com.javasea.file.preview.utils;
import org.jodconverter.OfficeDocumentConverter;
import org.jodconverter.office.DefaultOfficeManagerBuilder;
import org.jodconverter.office.OfficeException;
import org.jodconverter.office.OfficeManager;

import java.io.File;
import java.util.regex.Pattern;

/**
 * Created by lenovo12 on 2018/8/18.
 * doc docx ex.. ex..x ppt pptx
 */
public final class Office2PDF {
    private Office2PDF(){}

    /**
     * 将office格式的文件转为pdf
     * @param sourceFilePath 源文件路径
     * @return
     */
    public static File openOfficeToPDF(String sourceFilePath){
        return office2pdf(sourceFilePath);
    }

    /**
     * 将office文档转换为pdf文档
     * @param sourceFilePath 原文件路径
     * @return
     */
    public static File office2pdf(String sourceFilePath){
        OfficeManager officeManager = null;
        try{
            if(StringUtil.isEmpty(sourceFilePath))
            {
                //打印日志...
                return null;
            }
            File sourceFile = new File(sourceFilePath);
            if(!sourceFile.exists())
            {
                //打印日志...
                return null;
            }

            String after_convert_file_path = getAfterConverFilePath(sourceFilePath);
            //启动openOffice
            officeManager = getOfficeManager();
            OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
            return convertFile(sourceFile,after_convert_file_path,sourceFilePath,converter);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("转换异常");
        }finally {
           if(officeManager != null){
               try {
                   officeManager.stop();
               } catch (OfficeException e) {
                   e.printStackTrace();
               }
           }
        }
        return null;
    }

    /**
     * 转换文件
     * @param sourceFile 原文件
     * @param after_convert_file_path 转换后存放位置
     * @param sourceFilePath 原文件路径
     * @param converter 转换器
     * @return
     */
    public static File convertFile(File sourceFile,
                           String after_convert_file_path,String sourceFilePath,OfficeDocumentConverter converter) throws OfficeException {
        File outputFile = new File(after_convert_file_path);
        if(!outputFile.getParentFile().exists()){
            //如果上级目录不存在也就是E:/pdfFile这个文件夹不存在则创建一个
            outputFile.getParentFile().mkdirs();
        }
            converter.convert(sourceFile,outputFile);
        return outputFile;
    }

    public static OfficeManager getOfficeManager(){
        DefaultOfficeManagerBuilder builder = new DefaultOfficeManagerBuilder();
        builder.setOfficeHome(getOfficeHomeLocal());
        OfficeManager officeManager = builder.build();
        try {
            officeManager.start();
        } catch (OfficeException e) {
            //打印日志
            System.out.println("start openOffice Fail!");
            e.printStackTrace();
        }
        return officeManager;
    }

    /**
     * 获取转换后文件存放的路径
     * @param sourceFilePath 源文件
     * @return
     */
    public static String getAfterConverFilePath(String sourceFilePath){
        //截取源文件文件名
        String sourceFileName = sourceFilePath.substring(sourceFilePath.lastIndexOf("/") + 1);
        return "E:/pdfFile/" + sourceFileName.replaceAll("\\."+FileUtil.getFileSuffix(sourceFileName),".pdf");
    }

    /**
     * 获取openOffice的安装目录
     * @return
     */
    public static String getOfficeHomeLocal(){
        String osName = System.getProperty("os.name");
        if(Pattern.matches("Windows.*",osName))
        {
            return "C:/Program Files (x86)/OpenOffice 4";
        }
        else if(Pattern.matches("Linux.*",osName))
        {
            return "/usr/temp";
        }
        else if (Pattern.matches("Mac.*",osName))
        {
            return "/Application/openOfficeSoft";
        }
        return null;
    }
}
