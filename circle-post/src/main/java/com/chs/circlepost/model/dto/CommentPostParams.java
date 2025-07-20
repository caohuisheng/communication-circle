package com.chs.circlepost.model.dto;

import lombok.Data;

/**
 * Author: chs
 * Description:
 * CreateTime: 2025-07-06
 */
@Data
public class CommentPostParams {

    /**
     * 标题
     */
    private Integer postId;

    /**
     * 评论id（如果评论的帖子，为空；如果评论的其它评论，为其它评论的id）
     */
    private Integer commentId;

    /**
     * 上级评论id
     */
    private Integer parentId;

    /**
     * 评论内容
     */
    private String content;
}
