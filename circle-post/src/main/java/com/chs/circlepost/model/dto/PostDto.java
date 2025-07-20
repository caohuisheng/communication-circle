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

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 收藏数
     */
    private Integer collectCount;

    /**
     * 用户信息
     */
    private UserDto userInfo;

    /**
     * 是否点赞
     */
    private boolean isLike;

    /**
     * 是否收藏
     */
    private boolean isCollect;

    /**
     * 发布地
     */
    private String postPlace;

}
