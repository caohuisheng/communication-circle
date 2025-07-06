package com.chs.circlepost.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chs.circlepost.mapper.UserCommentPostMapper;
import com.chs.circlepost.model.po.UserCommentPost;
import com.chs.circlepost.service.UserCommentPostService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户评论帖子表 服务实现类
 * </p>
 *
 * @author chs
 * @since 2025-06-29
 */
@Service
public class UserCommentPostServiceImpl extends ServiceImpl<UserCommentPostMapper, UserCommentPost> implements UserCommentPostService {

}
