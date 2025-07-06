package com.chs.circlepost.model.dto;

import com.chs.circlepost.model.po.Post;
import lombok.Data;

/**
 * Author: chs
 * Description: 帖子dto类
 * CreateTime: 2025-07-06
 */
@Data
public class PostDto extends Post {

    /**
     * 分类
     */
    private String genre;

    /**
     * 主题
     */
    private String topic;

    /**
     * 圈子
     */
    private String circle;

}
