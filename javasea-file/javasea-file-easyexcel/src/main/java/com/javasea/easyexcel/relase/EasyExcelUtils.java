package com.javasea.easyexcel.relase;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.analysis.ExcelReadExecutor;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.javasea.easyexcel.entity.Teacher;
import com.javasea.easyexcel.relase.entity.ImportFile;
import com.javasea.easyexcel.relase.executor.HandeExecutor;
import com.javasea.easyexcel.relase.executor.HandeSheetExecutor;
import com.javasea.easyexcel.relase.listener.ExcelImportDataListener;
import com.javasea.easyexcel.relase.listener.ExcelImportSheetDataListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.util.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @ClassName EasyExcelUtils
 * @Description EasyExcel 工具类
 * @Author longxiaonan@163.com
 * @Date 2020/3/25 0025 12:07
 */
@Slf4j
public class EasyExcelUtils {

    /**
     * 将本地文件 进行 导入处理
     * @param fileName
     * @param clazz
     * @param executor
     * @param <T>
     */
    public static <T> void importExcel(String fileName, Class clazz, HandeExecutor executor) {

        ExcelImportDataListener<T> excelImportDataListener = new ExcelImportDataListener<>();

        excelImportDataListener.setHandeExecutor(executor);

        EasyExcel.read(fileName, clazz, excelImportDataListener).sheet().doRead();
    }

    /**
     * 将 文件流 进行导入处理, 读取第一个sheet
     * @param file
     * @param clazz
     * @param executor
     * @param <T>
     */
    public static <T> void importExcel(MultipartFile file, Class clazz, Integer headRowNumber, HandeExecutor executor) throws IOException {

        importExcel(file.getInputStream(), clazz, headRowNumber, null, executor);

    }

    /**
     * 将 文件流 进行导入处理, 读取第一个sheet
     * @param file
     * @param clazz
     * @param executor
     * @param <T>
     */
    public static <T> void importExcel(MultipartFile file, Class clazz, Integer headRowNumber, Map<Integer,String> headNameMap, HandeExecutor executor) throws IOException {

        importExcel(file.getInputStream(), clazz, headRowNumber, headNameMap, executor);

    }

    /**
     * 将 文件流 进行导入处理, 读取第一个sheet
     * @param inputStream
     * @param clazz
     * @param executor
     * @param <T>
     */
    public static <T> void importExcel(InputStream inputStream, Class clazz, Integer headRowNumber, Map<Integer, String> headNameMap, HandeExecutor executor) {

        ExcelImportDataListener<T> excelImportDataListener = new ExcelImportDataListener<>();
        if (null != headNameMap) {
            excelImportDataListener.setHeadNameMap(headNameMap);
        }
        excelImportDataListener.setHandeExecutor(executor);
        EasyExcel.read(inputStream, clazz, excelImportDataListener).sheet().headRowNumber(headRowNumber).doRead();
    }

    /**
     * 读取 Excel(多个 sheet), 传入执行器进行业务逻辑
     * @param file 上传的文件
     * @param tClass 实体类的类名
     * @param headRowNumber 列头的行数
     * @param sheetExecutor 读取sheet页的数据执行器
     * @return
     * @throws IOException
     */
    public static <T> void importExcel(MultipartFile file, Class<T> tClass, Integer headRowNumber, Map<Integer, String> headNameMap, HandeSheetExecutor sheetExecutor) throws IOException {
        importExcel(file.getInputStream(), tClass, headRowNumber, headNameMap,sheetExecutor);
    }

    /**
     * 读取 Excel(多个 sheet), 传入执行器进行业务逻辑
     * @param file 需要处理的文件
     * @param tClass 实体类的类名
     * @param headRowNumber 列头的行数
     * @param sheetExecutor 读取sheet页的数据执行器
     * @return
     * @throws IOException
     */
    public static <T> void importExcel(ImportFile file, Class<T> tClass, Integer headRowNumber, Map<Integer, String> headNameMap, HandeSheetExecutor sheetExecutor) throws IOException {
        importExcel(file.getInputStream(), tClass, headRowNumber, headNameMap,sheetExecutor);
    }

    /**
     * 读取 Excel(多个 sheet), 传入执行器进行业务逻辑
     * @param inputStream 需要处理的文件流
     * @param tClass 实体类的类名
     * @param headRowNumber 列头的行数
     * @param sheetExecutor 读取sheet页的数据执行器
     * @return
     * @throws IOException
     */
    public static <T> void importExcel(InputStream inputStream, Class<T> tClass, Integer headRowNumber, Map<Integer, String> headNameMap, HandeSheetExecutor sheetExecutor) throws IOException {
        ExcelReader reader = EasyExcel.read(inputStream).build();

        ExcelReadExecutor excelReadExecutor = reader.excelExecutor();
        List<ReadSheet> readSheets = excelReadExecutor.sheetList();
        if (null == headRowNumber) {
            headRowNumber = 1;
        }
        for (ReadSheet sheet : readSheets) {
            if (tClass != null) {
                sheet.setClazz(tClass);
            }
            Integer sheetNo = sheet.getSheetNo();
            String shtName = sheet.getSheetName();
            ExcelImportSheetDataListener<T> excelListener = new ExcelImportSheetDataListener<>();
            excelListener.setSheetName(shtName);
            excelListener.setHeadNameMap(headNameMap);
            excelListener.setHandeExecutor(sheetExecutor);
            ReadSheet readSheet = EasyExcel.readSheet(sheetNo).head(tClass).headRowNumber(headRowNumber).registerReadListener(excelListener).build();
            reader.read(readSheet);
        }
        reader.finish();

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

    private static List<ReadSheet> getReadSheets(MultipartFile file) {
        ExcelImportDataListener excelListener = new ExcelImportDataListener();
        ExcelReader reader = getReader(file, excelListener);
        ExcelReadExecutor excelExecutor = reader.excelExecutor();
        List<ReadSheet> readSheets = excelExecutor.sheetList();
        return readSheets;
    }

    /**
     * 下载excel
     * @param templateName 模板文件名
     * @param newName 重新设置后的文件名
     * @param path 路径
     * @param response
     */
    public static void downTemplate(String templateName, String newName, String path, HttpServletResponse response) {
        try (InputStream inputStream = new FileInputStream(new File(path, templateName + ".xlsx"));
             OutputStream outputStream = response.getOutputStream()) {
            response.reset();
            response.addHeader("Content-type", "application/octet-stream;charset=UTF-8");
            log.info( "-------------downTempleFileCommon path----------" + path + templateName + ".xlsx");
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + newName + ".xlsx");
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("文件下载出错", e);
        }
    }

    /**
     * 导出数据到excel
     * @param fileName 文件名称
     * @param sheetName sheet页名称
     * @param clazz 封装的实体名称
     * @param list 数据list
     * @param response
     * @param <T>
     * @throws IOException
     */
    public static <T> void exportExcel(String fileName, String sheetName, Class<T> clazz, List<T> list, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String newFileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + newFileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), clazz).sheet(sheetName).doWrite(list);
    }
}
