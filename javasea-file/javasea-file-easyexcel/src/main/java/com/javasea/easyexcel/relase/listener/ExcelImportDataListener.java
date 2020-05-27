package com.javasea.easyexcel.relase.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.javasea.easyexcel.relase.ExcelException;
import com.javasea.easyexcel.relase.executor.HandeExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName ExcelImportDataListener
 * @Description 监听器这个类不能够被Spring管理，每次使用单独的new出来
 * @Author longxiaonan@163.com
 * @Date 2020/3/23 0023 17:45
 */
@Slf4j
public class ExcelImportDataListener<T> extends AnalysisEventListener<T> {
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 500;

    /** 当监听器 只用于 sheet页时 */
    private String sheetName;

    /**
     * 这个集合用于接收 读取Excel文件得到的数据
     */
    private List<T> list = new ArrayList();

    private HandeExecutor<T> handeExecutor;

    private Map<Integer,String> headNameMap;

    public void setHeadNameMap(Map<Integer, String> headNameMap) {
        this.headNameMap = headNameMap;
    }

    /**
     * 这里会一行行的返回头
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        if (headNameMap != null) {
            headNameMap.forEach((k,v) -> {
                String headNameVale = headMap.get(k);
                if (!Objects.equals(headNameVale,v)) {
                    throw new ExcelException("表格表头和模板不一致");
                }
            });
        }
        log.info("解析到一条头数据:{}", headMap);
    }

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        list.add(t);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            handeData();
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用  *
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        handeData();
    }

    /**
     * 数据处理
     */
    private void handeData() {

        handeExecutor.exec(list);

    }


    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setHandeExecutor(HandeExecutor<T> handeExecutor) {
        this.handeExecutor = handeExecutor;
    }

}
