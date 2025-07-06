package com.chs.base.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Author: chs
 * Description:
 * CreateTime: 2025-07-06
 */
@Data
public class PageResult<T> implements Serializable {

    //分页查询结果
    private List<T> records;
    //记录总数
    private long count;

    public PageResult(List<T> records, long count) {
        this.records = records;
        this.count = count;
    }

}
