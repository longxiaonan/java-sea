package com.iee.file.upload.controller;

import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @ClassName ExcelUtils
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2020/1/16 0016 12:35
 */
public class ExcelUtils {
    private static TaskExecutePool taskExecutePool = SpringContextUtil.getBean(TaskExecutePool.class);

    //2003- 版本的excel
    private final static String excel2003L = ".xls";
    //2007+ 版本的excel
    private final static String excel2007U = ".xlsx";

    public static List<String>  readExcel(MultipartFile file) throws Exception{
        String originalFilename = file.getOriginalFilename();
        Workbook workbook = getWorkbook(file.getInputStream(), originalFilename);
        List<Future<List<String>>> futures = new ArrayList<>(5);
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            Sheet sheetAt = workbook.getSheetAt(numSheet); // 得到第几页的对象
            if (null == sheetAt) { // 如果为空就跳过当前页，执行下一页对象
                continue;
            }
            // 得到当前页的对象的名字
            String sheetName = sheetAt.getSheetName();
            // 取得有效的行数(Row)
            int lastRowNum = getExcelRealRow(sheetAt);
            futures.add(taskExecutePool.submit(new Callable<List<String>>(){
                @Override
                public List<String> call() throws Exception {
                    List list = new ArrayList();
                    for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
                        Row row = sheetAt.getRow(rowNum);
                        if (row != null) {
                            // 如果当前行的对象不为空
                            // 单纯的判断是否为null是不正确的所以我加了后半部分row.getCell().getCellType()!=CellType.BLANK，来验证是否为空
                            // 把每个单元格的值付给对象的对应属性
                            short lastCellNum = row.getLastCellNum();
                            for (int cellNum = 0; cellNum <= lastCellNum; cellNum++){
                                String cellContent = String.valueOf(getCellValue(row.getCell(cellNum)));
                                list.add(cellContent);
                            }
                        }
                    }
                    return list;
                }
            }));
        }
        List list = new ArrayList();
        for (Future<List<String>> future : futures) {
            //合并操作
            try {
                //处理
                list.addAll(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return  list;
    }

    public static  String getCellValue(Cell cell) {
        String value = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case FORMULA:
                    // cell.getCellFormula();
                    try {
                        value = String.valueOf(cell.getNumericCellValue());
                    } catch (IllegalStateException e) {
                        value = String.valueOf(cell.getRichStringCellValue());
                    }
                    break;
                case NUMERIC:
                    value = String.valueOf(cell.getNumericCellValue());
                    break;
                case STRING:
                    value = String.valueOf(cell.getRichStringCellValue());
                    break;
            }
        }
        return value;
    }

    /**
     * 判断是否是空行 （即没有任何数据，或许有格式）
     */
    private static boolean isBlankRow(Row row) {
        if (row == null) {
            return true;
        }
        boolean result = true;
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            String value = "";
            if (cell != null) {
                switch (cell.getCellType()) {
                    case STRING:
                        value = cell.getStringCellValue();
                        break;
                    case NUMERIC: //Cell.CELL_TYPE_NUMERIC:
                        value = String.valueOf((int) cell.getNumericCellValue());
                        break;
                    case BOOLEAN: //Cell.CELL_TYPE_BOOLEAN:
                        value = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case FORMULA: //Cell.CELL_TYPE_FORMULA:
                        value = String.valueOf(cell.getCellFormula());
                        break;
                    //case CellType.BLANK:
                    //    break;
                    default:
                        break;
                }

                if (!value.trim().equals("")) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     */
    private static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        //创建Excel文档
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            // wb = new HSSFWorkbook(inStr);  //2003-
            //2003-
            wb = WorkbookFactory.create(inStr);
        } else if (excel2007U.equals(fileType)) {
            // wb = new XSSFWorkbook(inStr);  //2007+
            //2007+
            wb = WorkbookFactory.create(inStr);
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    /**
     * 获取Excel表的真实行数,并且删除空行（即没有任何数据，或许有格式）
     */
    private static int getExcelRealRow(Sheet sheet) {
        String sheetName = sheet.getSheetName();
        System.err.println(sheetName);
        boolean flag = false;
        for (int i = 1; i < sheet.getLastRowNum(); ) {
            Row r = sheet.getRow(i);
            if (r == null || isBlankRow(r)) {
                // 如果是空行（即没有任何数据，或许有格式），直接把它以下的数据往上移动
                sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                continue;
            }
            flag = false;
            for (Cell c : r) {
                if (c.getCellType() != CellType.BLANK) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                i++;
                continue;
            } else {
                // 如果是空白行（即可能没有数据，但是有一定格式）
                // 如果到了最后一行，直接将那一行remove掉
                if (i == sheet.getLastRowNum()) {
                    sheet.removeRow(r);
                    //如果还没到最后一行，则数据往上移一行
                } else {
                    sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                }
            }
        }
        return sheet.getLastRowNum();
    }
}
