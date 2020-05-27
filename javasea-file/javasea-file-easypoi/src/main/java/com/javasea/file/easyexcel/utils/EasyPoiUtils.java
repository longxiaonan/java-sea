package com.javasea.file.easyexcel.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.javasea.file.easyexcel.entity.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName EasyPoiUtils
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2020/3/5  0005  23:43
 */
@Slf4j
public class EasyPoiUtils {

    /**
     * easypoi导入
     * @param inputStream 输入流
     * @param titleRows 标题行数
     * @param headRows  表头行数
     * @param clazz  转换的实体名称
     * @throws Exception
     */
    public static void importExcel(InputStream inputStream, Integer titleRows, Integer headRows, Class clazz) throws Exception {
        /**         *  ImportParams  导入参数对象         *  定义标题栏和表头数据         */
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(titleRows);
        importParams.setHeadRows(headRows);
        /***  导入方法         
         * *  参数1  流  读取要导入的文件         
         * *  参数2  要导入的实体类的类对象  上师对象的类对象         
         * *  参数3  导入参数对象         *         
         * *  返回值  导入数据  直接封装为集合对象         */
        List<T> teachers = ExcelImportUtil.importExcel(inputStream, clazz, importParams);
        System.out.println(teachers);
    }

    /**
     * easypoi 导出
     * @param list 需要到出的数据列表
     * @param clazz 实体class
     * @param title 标题
     * @param sheetName sheet页名称
     * @param outPath 输出目录
     * @throws IOException
     */
    public static void exportExcel(List list, Class clazz, String title, String sheetName, String outPath) throws IOException {
        //    模拟数据
        /**      *  导出参数对象      *  参数1  标题      *  参数2  表的名字      */
        ExportParams exportParams = new ExportParams(title, sheetName);
        /**      *  exportExcel  导出Excel文件      *  参数1  导出参数对象      *  参数2  要导出的实体类的类对象      *  参数3  要导出的数据  需要一个集合  数据库查询出来的老师对象的集合      *      *  返回值就是封装好的文件对象      */
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Teacher.class, list);
        workbook.write(new FileOutputStream(outPath));
    }

    /**
     * 下载excel
     * @param oldName 旧文件名
     * @param newName 重新设置后的文件名
     * @param path 路径
     * @param response
     */
    public static void downExcel(String oldName, String newName, String path, HttpServletResponse response) {
        try (InputStream inputStream = new FileInputStream(new File(path, oldName + ".xlsx"));
             OutputStream outputStream = response.getOutputStream()) {
            response.reset();
            /* 设置ContentType字段值“text/html”，“application/json”
             response.setContentType("application/x-download;charset=UTF-8");
             octet-stream 二进制数据流，不知道是什么类型，参考：http://tools.jb51.net/table/http_content_type/
             */
            response.addHeader("Content-type", "application/octet-stream;charset=UTF-8");
            log.info( "-------------downTempleFileCommon path----------" + path + oldName + ".xlsx");
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + newName + ".xlsx");
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            log.error("下载模板时报错", e);
        }
    }

    @Test
    public void inputTest() throws Exception {
        /**     *  easypoi导入     */
        FileInputStream inputStream = new FileInputStream("D:/wms/teachers.xls");
        EasyPoiUtils.importExcel(inputStream, 1, 1 , Teacher.class);
    }

    @Test
    public void outputTest() throws IOException {
        //    模拟数据
        List<Teacher> list = new ArrayList<>();
        list.add(new Teacher(1, "李老师", "hhh.jpg", 1));
        list.add(new Teacher(2, "李老师", "hhh.jpg", 1));
        list.add(new Teacher(3, "李老师", "hhh.jpg", 1));
        list.add(new Teacher(4, "李老师", "hhh.jpg", 1));
        list.add(new Teacher(5, "李老师", "hhh.jpg", 1));
        list.add(new Teacher(6, "李老师", "hhh.jpg", 1));

        EasyPoiUtils.exportExcel(list,Teacher.class,"教师状态", "teacher2", "D:/wms/teachers2.xls");
    }

}
