package com.chs.circlepost.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户评论帖子表
 * </p>
 *
 * @author chs
 * @since 2025-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserCommentPost implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 帖子id
     */
    private Integer postId;

    /**
     * 内容
     */
    private String content;

    /**
     * 评论的是帖子，为空 评论的是评论，表示评论的id
     */
    private Integer commentId;

    /**
     * 上级评论id
     */
    private Integer parentId;

    /**
     * 评论时间
     */
    private LocalDateTime commentTime;


}
