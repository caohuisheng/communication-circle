package com.chs.circlepost.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author chs
 * @since 2025-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 签名
     */
    private String signature;

    /**
     * 居住地
     */
    private String place;

    /**
     * 毕业年份
     */
    private Integer graduateYear;

    /**
     * 学历
     */
    private Integer background;

    /**
     * 专业id
     */
    private Integer majorId;

    /**
     * 学校id
     */
    private Integer schoolId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
