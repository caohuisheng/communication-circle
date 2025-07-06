package com.chs.circlepost.model.dto;

import lombok.Data;

/**
 * Author: chs
 * Description:
 * CreateTime: 2025-07-06
 */
@Data
public class QueryPostParams {

    /**
     * 标题
     */
    private String keyWord;

    /**
     * 分类id
     */
    private Integer genreId;

    /**
     * 主题id
     */
    private Integer topicId;

    /**
     * 页数
     */
    private Integer page = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 20;

}
