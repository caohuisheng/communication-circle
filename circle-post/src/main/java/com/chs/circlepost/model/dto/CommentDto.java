package com.chs.circlepost.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Author: chs
 * Description:
 * CreateTime: 2025-07-20
 */
@Data
public class CommentDto {

    private Integer id;
    private String content;
    private LocalDateTime commentTime;
    // 评论者的用户信息
    private UserDto userInfo;
    // 回复评论的用户信息
    private UserDto replyUserInfo;
    // 当前评论下的的其它评论
    private List<CommentDto> childrens;

}
