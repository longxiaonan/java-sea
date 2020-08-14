package com.javasea.easyexcel.relase.merge;

import lombok.Data;

@Data
public class GenericMergeBO {
    /**
     * 是否是开始合并的单元格
     */
    private Boolean startMergeCell;
    /**
     * 合并的行数
     */
    private Integer rowspan;
    /**
     * 合并的列数
     */
    private Integer colspan;
}
