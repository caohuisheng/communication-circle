package com.chs.circlepost.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chs.circlepost.mapper.UserLikePostMapper;
import com.chs.circlepost.model.po.UserLikePost;
import com.chs.circlepost.service.UserLikePostService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户点赞帖子表 服务实现类
 * </p>
 *
 * @author chs
 * @since 2025-06-29
 */
@Service
public class UserLikePostServiceImpl extends ServiceImpl<UserLikePostMapper, UserLikePost> implements UserLikePostService {

}
