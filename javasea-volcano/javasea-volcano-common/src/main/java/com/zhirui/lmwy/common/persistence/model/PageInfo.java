package com.zhirui.lmwy.common.persistence.model;
/**
 * @Description 分页实体，用于封装分页参数
 * @Author longxiaonan@163.com
 * @Date 13:30 2019/6/26 0026
 **/
public class PageInfo {

    private int pageSize;//一页显示多少行
    private int pageIndex;//页码
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
}
