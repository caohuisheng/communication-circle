package com.chs.circlepost.model.dto;

import lombok.Data;

/**
 * Author: chs
 * Description:
 * CreateTime: 2025-07-06
 */
@Data
public class QueryCommentParams {

    /**
     * 帖子id
     */
    private Integer postId;

    /**
     * 页数
     */
    private Integer page;

    /**
     * 每页大小
     */
    private Integer pageSize;
}
