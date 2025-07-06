package com.chs.circlepost.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chs.circlepost.mapper.UserMapper;
import com.chs.circlepost.model.po.User;
import com.chs.circlepost.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author chs
 * @since 2025-06-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
