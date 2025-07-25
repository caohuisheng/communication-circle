package com.chs.circlepost.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Author: chs
 * Description: 添加或更新帖子参数
 * CreateTime: 2025-07-05
 */
@Data
public class AddUpdatePostParams {
    /**
     * 帖子id
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    @NotBlank
    private String content;

    /**
     * 分类id
     */
    @NotNull
    private Integer genreId;

    /**
     * 分类id
     */
    private Integer circleId;
}
