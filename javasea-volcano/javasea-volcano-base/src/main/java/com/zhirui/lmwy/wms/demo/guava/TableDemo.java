package com.zhirui.lmwy.wms.demo.guava;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.Map;

public class TableDemo {
    public static void main(String[] args) {

        Table<Integer, String, String> table = HashBasedTable.create();
        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 2; column++) {
                table.put(row, "a"+column, "value of cell (" + row + ",a" + column + ")");
            }
        }
        for (int row=0;row<table.rowMap().size();row++) {
            Map<String,String> rowData = table.row(row);
            for (int column =0;column < rowData.size(); column ++) {
                System.out.println("cell(" + row + "," + column + ") value is:" + rowData.get("a"+column));
            }
        }
    }
}
