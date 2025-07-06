package com.chs.circlepost.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 帖子圈子表
 * </p>
 *
 * @author chs
 * @since 2025-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PostCircle implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 帖子id
     */
    private Integer postId;

    /**
     * 圈子id
     */
    private Integer circleId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
