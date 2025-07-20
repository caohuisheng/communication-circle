package com.chs.circlepost.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chs.base.model.PageResult;
import com.chs.circlepost.model.dto.CommentDto;
import com.chs.circlepost.model.dto.QueryCommentParams;
import com.chs.circlepost.model.po.UserCommentPost;

/**
 * <p>
 * 用户评论帖子表 服务类
 * </p>
 *
 * @author chs
 * @since 2025-06-29
 */
public interface UserCommentPostService extends IService<UserCommentPost> {

    /**
     * 查询帖子的评论
     */
    PageResult<CommentDto> queryPostComment(QueryCommentParams params);

}
