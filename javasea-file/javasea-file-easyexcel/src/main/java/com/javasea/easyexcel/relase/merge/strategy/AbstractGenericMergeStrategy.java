package com.javasea.easyexcel.relase.merge.strategy;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import com.javasea.easyexcel.relase.merge.CellPoint;
import com.javasea.easyexcel.relase.merge.GenericMerge;
import com.javasea.easyexcel.relase.merge.GenericMergeBO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * @Description https://blog.csdn.net/sinat_33472737/article/details/106339424
 * @Author longxiaonan@163.com
 * @Date 0:28 2020/8/12 0012
 **/
public abstract class AbstractGenericMergeStrategy<T extends GenericMerge> extends AbstractMergeStrategy {
    //所有数据 目的就是从所有数据找到当前行数据row
    protected List<T> list;
    //表头行数
    protected int headRowNumber = 0;

    protected AbstractGenericMergeStrategy(List<T> list){
        this.list = list;
    }
    protected AbstractGenericMergeStrategy(List<T> list, int headRowNumber){
        this.list = list;
        this.headRowNumber = headRowNumber;
    }

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
        int columnIndex = cell.getColumnIndex();
        int rowIndex = cell.getRowIndex();
        //找到cell对应的当前行数据row
        T t = list.get(relativeRowIndex);
        GenericMergeBO bo = t.merge(columnIndex);
        if (bo != null){
            merge(sheet,cell,bo);
        }
    }

    protected abstract void merge(Sheet sheet, Cell cell,GenericMergeBO bo);

    protected void executorMerge(CellPoint cellPoint, Sheet sheet){
            CellRangeAddress cellRangeAddress = new CellRangeAddress(
                    cellPoint.getStartY(),
                    cellPoint.getEndY(),
                    cellPoint.getStartX(),
                    cellPoint.getEndX());
            sheet.addMergedRegionUnsafe(cellRangeAddress);
    }

    String getCellText(Cell cell){
        String text = "";
        CellType cellType = cell.getCellTypeEnum();
        if(CellType.STRING.equals(cellType)){
            text = cell.getStringCellValue();
        }else if(CellType.NUMERIC.equals(cellType)){
            text = Double.toString(cell.getNumericCellValue());
        }else if(CellType.BOOLEAN.equals(cellType)){
            text = Boolean.toString(cell.getBooleanCellValue());
        }
        return text;
    }
}
