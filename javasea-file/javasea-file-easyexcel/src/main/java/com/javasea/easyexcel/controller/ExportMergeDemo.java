package com.javasea.easyexcel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasea.easyexcel.entity.Teacher;
import com.javasea.easyexcel.entity.Teacher2;
import com.javasea.easyexcel.relase.merge.strategy.GenericRowMergeStrategy;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.expression.Lists;

import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName ExportDemo
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2020/3/23 0023 17:28
 */
@RestController
public class ExportMergeDemo {

    @Autowired
    ObjectMapper objectMapper;

    /**
     * 基本的导出
     */
    @Test
    public void exportTest() {
        // 准备数据
        List<Teacher2> teachers2 = getTeacher2s();

        //根据分组设置参数
        setSpanParam(teachers2);

        String fileName = "d:/wms/aaaa.xls";

        // 导出方式一
        EasyExcel.write(fileName, Teacher2.class).registerWriteHandler(new GenericRowMergeStrategy(teachers2)).sheet().doWrite(teachers2);

        // 导出方式二, web方式导出附件
//        EasyExcel.write(new BufferedOutputStream(response.getOutputStream()), Teacher2.class).registerWriteHandler(mergeStrategy).sheet().doWrite(teachers2);

        // 导出方式三
//        List<String> list = Arrays.asList(new String[]{"老师姓名","头像地址","删除状态","学号","学生姓名","教室","宠物名称","宠物分类"});
//        List<List<String>> listlist = new ArrayList<>();
//        listlist.add(list);
//        ExcelWriter excelWriter = EasyExcel.write(fileName)
//                // 设置统一的表头、表内容样式
//                .registerWriteHandler(style())
//                // 设置表头，表内容的行高
//                .registerWriteHandler(new SimpleRowHeightStyleStrategy((short) 50, (short) 20))
//                .head(Teacher2.class)
//                .build();
//        WriteSheet sheet1 = EasyExcel.writerSheet(0, "班级分数表")
//                .registerWriteHandler(new GenericRowMergeStrategy(teachers2))
//                .build();
//        excelWriter.write(teachers2, sheet1);
//        excelWriter.finish();
    }

    private void setSpanParam(List<Teacher2> teachers2) {
        Map<String, List<Teacher2>> nameMap = teachers2.stream().collect(Collectors.groupingBy(Teacher2::getTeacherName));
        nameMap.forEach((k,v)-> {
            int index = v.size();
            for (Teacher2 dto : v) {
                //给0,1,2,8列设置boolean参数和需要合并的行数
                dto.setIsRowSpan(index == v.size());
                dto.setRowIndexNumber(v.size());
                index--;
            }
            //每一个用户下又根据项目编号分组
            Map<String, List<Teacher2>> projectMap = v.stream().collect(Collectors.groupingBy(Teacher2::getStudentName));
            projectMap.forEach((k1, v1) -> {
                int j = v1.size();
                for (Teacher2 dto : v1) {
                    //给3,4列设置boolean参数和需要合并的行数
                    dto.setIsStudentRowSpan(j == v1.size());
                    dto.setStudentRowIndexNumber(v1.size());
                    j--;
                }
            });
        });
    }

    private List<Teacher2> getTeacher2s() {
        List<Teacher2> teachers2 = new ArrayList<>();

        Teacher2 tPetaa21 = new Teacher2();
        tPetaa21.setTeacherName("aa");
        tPetaa21.setStudentName("aa2");
        tPetaa21.setPetName("aa21");
        teachers2.add(tPetaa21);

        Teacher2 tPetaa11 = new Teacher2();
        tPetaa11.setTeacherName("aa");
        tPetaa11.setStudentName("aa1");
        tPetaa11.setPetName("aa11");
        teachers2.add(tPetaa11);

        Teacher2 tPetaa12 = new Teacher2();
        tPetaa12.setTeacherName("aa");
        tPetaa12.setStudentName("aa1");
        tPetaa12.setPetName("aa12");
        teachers2.add(tPetaa12);

        Teacher2 tPetbb11 = new Teacher2();
        tPetbb11.setTeacherName("bb");
        tPetbb11.setStudentName("bb1");
        tPetbb11.setPetName("bb12");
        teachers2.add(tPetbb11);

        Teacher2 tPetbb12 = new Teacher2();
        tPetbb12.setTeacherName("bb");
        tPetbb12.setStudentName("bb1");
        tPetbb12.setPetName("bb11");
        teachers2.add(tPetbb12);

        Teacher2 tPetbb22 = new Teacher2();
        tPetbb22.setTeacherName("bb");
        tPetbb22.setStudentName("bb2");
        tPetbb22.setPetName("bb22");
        teachers2.add(tPetbb22);

        Teacher2 tPetcc11 = new Teacher2();
        tPetcc11.setTeacherName("cc");
        tPetcc11.setStudentName("cc1");
        tPetcc11.setPetName("cc11");
        teachers2.add(tPetcc11);

        Teacher2 tPetcc12 = new Teacher2();
        tPetcc12.setTeacherName("cc");
        tPetcc12.setStudentName("cc1");
        tPetcc12.setPetName("cc12");
        teachers2.add(tPetcc12);

        Teacher2 tPetcc13 = new Teacher2();
        tPetcc13.setTeacherName("cc");
        tPetcc13.setStudentName("cc1");
        tPetcc13.setPetName("cc13");
        teachers2.add(tPetcc13);

        Teacher2 tPetdd11 = new Teacher2();
        tPetdd11.setTeacherName("dd");
        tPetdd11.setStudentName("dd1");
        tPetdd11.setPetName("dd11");
        teachers2.add(tPetdd11);

        Teacher2 tPetdd12 = new Teacher2();
        tPetdd12.setTeacherName("dd");
        tPetdd12.setStudentName("dd1");
        tPetdd12.setPetName("dd12");
        teachers2.add(tPetdd12);

        Teacher2 tPetdd13 = new Teacher2();
        tPetdd13.setTeacherName("dd");
        tPetdd13.setStudentName("dd1");
        tPetdd13.setPetName("dd13");
        teachers2.add(tPetdd13);

        Teacher2 tPetdd21 = new Teacher2();
        tPetdd21.setTeacherName("dd");
        tPetdd21.setStudentName("dd2");
        tPetdd21.setPetName("dd21");
        teachers2.add(tPetdd21);

        Teacher2 tPetdd22 = new Teacher2();
        tPetdd22.setTeacherName("dd");
        tPetdd22.setStudentName("dd3");
        tPetdd22.setPetName("dd31");
        teachers2.add(tPetdd22);

        Teacher2 tPetdd23 = new Teacher2();
        tPetdd23.setTeacherName("dd");
        tPetdd23.setStudentName("dd3");
        tPetdd23.setPetName("dd32");
        teachers2.add(tPetdd23);
        return teachers2;
    }

    public static HorizontalCellStyleStrategy style() {
        // 头的样式
        WriteCellStyle headStyle = new WriteCellStyle();
        headStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        WriteFont headFont = new WriteFont();
        headFont.setBold(true);
        headFont.setFontHeightInPoints((short) 16);
        headStyle.setWriteFont(headFont);
        headStyle.setWrapped(true);

        // 内容的样式
        WriteCellStyle contentStyle = new WriteCellStyle();
        contentStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        contentStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short) 12);
        contentStyle.setWriteFont(contentWriteFont);
        contentStyle.setBorderTop(BorderStyle.THIN);
        contentStyle.setBorderBottom(BorderStyle.THIN);
        contentStyle.setBorderLeft(BorderStyle.THIN);
        contentStyle.setBorderRight(BorderStyle.THIN);
        contentStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentStyle.setWrapped(true);
        return new HorizontalCellStyleStrategy(headStyle, contentStyle);
    }

}
