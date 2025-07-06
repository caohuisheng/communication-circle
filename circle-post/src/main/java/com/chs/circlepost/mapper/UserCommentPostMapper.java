package com.chs.circlepost.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chs.circlepost.model.po.UserCommentPost;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户评论帖子表 Mapper 接口
 * </p>
 *
 * @author chs
 * @since 2025-06-29
 */
@Mapper
public interface UserCommentPostMapper extends BaseMapper<UserCommentPost> {

}
