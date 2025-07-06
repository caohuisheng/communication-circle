package com.chs.circlepost.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chs.circlepost.mapper.UserCollectPostMapper;
import com.chs.circlepost.model.po.UserCollectPost;
import com.chs.circlepost.service.UserCollectPostService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户收藏帖子表 服务实现类
 * </p>
 *
 * @author chs
 * @since 2025-06-29
 */
@Service
public class UserCollectPostServiceImpl extends ServiceImpl<UserCollectPostMapper, UserCollectPost> implements UserCollectPostService {

}
