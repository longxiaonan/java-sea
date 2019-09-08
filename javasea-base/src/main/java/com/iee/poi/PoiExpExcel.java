package com.iee.poi;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
    * @ClassName: PoiExpExcel
	* @Description:
    * @author 龙小南
    * @email longxiaonan@163.com
    * @date 2017年9月21日 上午12:41:57
    * @version 1.0
    */
public class PoiExpExcel {
	public static void main(String[] args) {
		//创建一个excel文件, 并且导出到本地
//		createAndExpExcel();
		//读取本地一个excel文件, 读取到excel对象后打印
//		readExcel();

	}

	private static void readExcel() {
		File file = new File("e:/poi_test.xls");	//只能是xls文件, 即97到2003版本的excel
		//创建excel, 读取文件内容
		HSSFWorkbook workbook = new HSSFWorkbook();
		//读取sheet方式一: 打开poi_test.xls, 读取名字是"Sheet0"的工作表
		//HSSFSheet sheet = workbook.createSheet("Sheet0");
		//根据表格中的sheet编号获取sheet, 0表示是第一张sheet
		HSSFSheet sheet = workbook.getSheetAt(0);
		int firstRowNum = 0;	//从第一行开始
		int lastRowNum = sheet.getLastRowNum(); //到最后一行
		for (int i = firstRowNum; i <= lastRowNum; i++){
			HSSFRow row = sheet.getRow(i);
			//获取当前行最后单元格列号
			int lastCellNum = row.getLastCellNum();
			for(int j = 0; j < lastCellNum; j++){
				HSSFCell cell = row.getCell(j);
				String value = cell.getStringCellValue();//还可以是int, date等类型
				System.out.print(value + " ");
			}
			System.out.println();
		}

	}

	private static void createAndExpExcel() {
		//表头定义
		String[] title = {"id","name","sex"};

		//创建Excel工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建一个工作表sheet
		HSSFSheet sheet = workbook.createSheet();
		//创建第一行,从0开始
		HSSFRow row = sheet.createRow(0);//第一行
		HSSFCell cell = null;
		//插入第一行, 值为id, name, sex
		for(int i = 0; i < title.length; i++){
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		//追加数据, 第2行到第11行插入数据
		for(int i = 1; i <= 10; i++){
			HSSFRow nextrow = sheet.createRow(i);//第二行开始
			HSSFCell cell2 = nextrow.createCell(0);
			cell2.setCellValue("a"+i);
			cell2 = nextrow.createCell(1);
			cell2.setCellValue("user"+i);
			cell2 = nextrow.createCell(2);
			cell2.setCellValue("男");
		}
		//创建一个文件
		File file = new File("e:/poi_test.xls");	//只能是xls文件, 即97到2003版本的excel
		try {
			file.createNewFile();
			FileOutputStream stream = FileUtils.openOutputStream(file);
			workbook.write(stream);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
