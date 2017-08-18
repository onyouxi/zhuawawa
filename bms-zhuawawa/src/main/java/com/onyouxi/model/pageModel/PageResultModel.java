package com.onyouxi.model.pageModel;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by administrator on 2017/8/18.
 */
public class PageResultModel<T> {
    private long total = 0;
    private List<T> rows;

    public PageResultModel() {
    }

    public PageResultModel(Page<T> page) {
        this.total = page.getTotalElements();
        this.rows = page.getContent();
    }

    public PageResultModel(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
