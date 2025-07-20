package com.chs.circlepost.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chs.circlepost.mapper.UserMapper;
import com.chs.circlepost.model.dto.UserDto;
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

    @Override
    public UserDto getUserInfo(Integer userId) {
        User user = this.getById(userId);
        UserDto userDto = new UserDto();
        userDto.setAvatar(user.getAvatar());
        userDto.setNickname(user.getNickname());
        // todo: 查询学校信息
        // todo：查询年级信息
        return userDto;
    }
}
