package com.javasea.easyexcel.helpful;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.javasea.easyexcel.relase.ExcelException;
import com.javasea.easyexcel.relase.listener.ExcelImportDataListener;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    /**
     * 读取某个 sheet 的 Excel
     *
     * @param excel 文件
     * @param rowModel 实体类映射，继承 BaseExcelModel 类
     * @param sheetNo sheet 的序号 从1开始
     * @return Excel 数据 list
     */
    public static List<BaseExcelModel> readExcel(MultipartFile excel, BaseExcelModel rowModel, int sheetNo) {
        return readExcel(excel, rowModel, sheetNo, 1);
    }

    /**
     * 读取某个 sheet 的 Excel
     *
     * @param excel 文件
     * @param rowModel 实体类映射，继承 BaseExcelModel 类
     * @param sheetNo sheet 的序号 从1开始
     * @param headLineNum 表头行数，默认为1
     * @return Excel 数据 list
     */
    public static List<BaseExcelModel> readExcel(MultipartFile excel, BaseExcelModel rowModel, int sheetNo, int headLineNum) {
        ExcelImportDataListener<BaseExcelModel> excelListener = new ExcelImportDataListener<>();
        ExcelReader reader = getReader(excel, excelListener);

        if (reader == null) {
            return null;
        }
        List<BaseExcelModel> modelList = new ArrayList<>();
        reader.read(new ReadSheet(sheetNo));
        excelListener.setHandeExecutor((list) -> modelList.addAll(list));
        return modelList;
    }

    /**
     * 导出 Excel ：一个 sheet，带表头
     *
     * @param response HttpServletResponse
     * @param list 数据 list，每个元素为一个 BaseExcelModel
     * @param fileName 导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param object 映射实体类，Excel 模型
     */
    public static void writeExcel(HttpServletResponse response, List<? extends BaseExcelModel> list, String fileName,
                                  String sheetName, BaseExcelModel object) {
        ExcelWriter writer = new ExcelWriter(getOutputStream(fileName, response), ExcelTypeEnum.XLSX);
        Sheet sheet = new Sheet(1, 0, object.getClass());
        sheet.setSheetName(sheetName);

        TableStyle tableStyle = new TableStyle();
        tableStyle.setTableContentBackGroundColor(IndexedColors.WHITE);
        Font font = new Font();
        font.setFontHeightInPoints((short) 9);
        tableStyle.setTableHeadFont(font);
        tableStyle.setTableContentFont(font);
        sheet.setTableStyle(tableStyle);

        writer.write(list, sheet);
        writer.finish();
    }

    /**
     * 导出 Excel ：多个 sheet，带表头
     *
     * @param response HttpServletResponse
     * @param list 数据 list，每个元素为一个 BaseExcelModel
     * @param fileName 导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param object 映射实体类，Excel 模型
     */
    public static ExcelWriterFactory writeExcelWithSheets(HttpServletResponse response,
                                                          List<? extends BaseExcelModel> list, String fileName,
                                                          String sheetName, BaseExcelModel object) {
        ExcelWriterFactory writer = new ExcelWriterFactory(getOutputStream(fileName, response), ExcelTypeEnum.XLSX);
        Sheet sheet = new Sheet(1, 0, object.getClass());
        sheet.setSheetName(sheetName);
        sheet.setTableStyle(getTableStyle());
        writer.write(list, sheet);

        return writer;
    }

    /**
     * 导出融资还款情况表
     *
     * @param response
     * @param list
     * @param fileName
     * @param sheetName
     * @param object
     */
    public static void writeFinanceRepayment(HttpServletResponse response, List<? extends BaseExcelModel> list,
                                             String fileName, String sheetName, BaseExcelModel object) {
        ExcelWriter writer = new ExcelWriter(getOutputStream(fileName, response), ExcelTypeEnum.XLSX);
        Sheet sheet = new Sheet(1, 0, object.getClass());
        sheet.setSheetName(sheetName);
        sheet.setTableStyle(getTableStyle());
        writer.write(list, sheet);

        for (int i = 1; i <= list.size(); i += 4) {
            writer.merge(i, i + 3, 0, 0);
            writer.merge(i, i + 3, 1, 1);
        }

        writer.finish();
    }

    /**
     * 导出文件时为Writer生成OutputStream
     */
    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) {
        //创建本地文件
        fileName = fileName + ".xls";

        try {
            fileName = new String(fileName.getBytes(), "ISO-8859-1");
            response.addHeader("Content-Disposition", "filename=" + fileName);

            return response.getOutputStream();
        } catch (Exception e) {

            throw new ExcelException("导出异常！");
        }
    }

    /**
     * 返回 ExcelReader
     *
     * @param excel 需要解析的 Excel 文件
     * @param excelListener new ExcelListener()
     */
    private static ExcelReader getReader(MultipartFile excel, ExcelImportDataListener excelListener) {
        String filename = excel.getOriginalFilename();

        if (filename == null || (!filename.toLowerCase().endsWith(".xls") && !filename.toLowerCase().endsWith(".xlsx"))) {
            throw new ExcelException("文件格式错误！");
        }

        try(InputStream inputStream = new BufferedInputStream(excel.getInputStream())) {

            return new ExcelReader(inputStream, null, excelListener, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 资金收支导出 Excel ：一个 sheet，带表头
     *
     * @param response HttpServletResponse
     * @param list 数据 list，每个元素为一个 BaseExcelModel
     * @param fileName 导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param object 映射实体类，Excel 模型
     */
    public static void exportFundBudgetExcel(HttpServletResponse response, List<? extends BaseExcelModel> list,
                                             String fileName, String sheetName, BaseExcelModel object) throws IOException {
        ExcelWriter writer = new ExcelWriter(getOutputStream(fileName, response), ExcelTypeEnum.XLSX);
        Sheet sheet = new Sheet(1, 0, object.getClass());
        sheet.setSheetName(sheetName);
        sheet.setTableStyle(getTableStyle());

        writer.write(list, sheet);
        writer.merge(2, 3, 0, 0);
        writer.merge(4, 13, 0, 0);
        writer.merge(14, 14, 0, 1);
        writer.finish();
    }

    /**
     * 读取Excel表格数据，封装成实体
     *
     * @param inputStream
     * @param clazz
     * @param sheetNo
     * @param headLineMun
     * @return
     */
//    public static Object readExcel(InputStream inputStream, Class<? extends BaseExcelModel> clazz, Integer sheetNo,
//                                   Integer headLineMun) {
//        if (null == inputStream) {
//
//            throw new NullPointerException("the inputStream is null!");
//        }
//
//        ExcelImportDataListener listener = new ExcelImportDataListener();
//        ExcelReader reader = new ExcelReader(inputStream, valueOf(inputStream), null, listener);
//        reader.read(new Sheet(sheetNo, headLineMun, clazz));
//
//        return listener.getDatas();
//    }

    /**
     * 根据输入流，判断为xls还是xlsx，该方法原本存在于easyexcel 1.1.0 的ExcelTypeEnum中。
     */
    public static ExcelTypeEnum valueOf(InputStream inputStream) {
        try {
            FileMagic fileMagic = FileMagic.valueOf(inputStream);

            if (FileMagic.OLE2.equals(fileMagic)) {
                return ExcelTypeEnum.XLS;
            }

            if (FileMagic.OOXML.equals(fileMagic)) {
                return ExcelTypeEnum.XLSX;
            }

            throw new ExcelException("excelTypeEnum can not null");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置全局样式
     *
     * @return
     */
    private static TableStyle getTableStyle() {
        TableStyle tableStyle = new TableStyle();

        tableStyle.setTableContentBackGroundColor(IndexedColors.WHITE);
        Font font = new Font();
        font.setBold(true);
        font.setFontHeightInPoints((short) 9);
        tableStyle.setTableHeadFont(font);
        Font fontContent = new Font();
        fontContent.setFontHeightInPoints((short) 9);
        tableStyle.setTableContentFont(fontContent);

        return tableStyle;
    }
}
