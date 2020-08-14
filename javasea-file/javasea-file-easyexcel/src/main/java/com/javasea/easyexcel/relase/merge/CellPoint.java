package com.javasea.easyexcel.relase.merge;

import lombok.Data;

@Data
public class CellPoint {
    /**
     * 开始单元格x坐标
     */
    private int startX;
    /**
     * 结束单元格x坐标
     */
    private int endX;
    /**
     * 开始单元格y坐标
     */
    private int startY;
    /**
     * 结束单元格y坐标
     */
    private int endY;
    /**
     * 单元格内容，文本
     */
    private String text;
}
