package com.chs.circlepost.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 话题表
 * </p>
 *
 * @author chs
 * @since 2025-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 话题id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 话题名
     */
    private String topicName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
