package com.chs.circlepost.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chs.circlepost.model.dto.UserDto;
import com.chs.circlepost.model.po.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author chs
 * @since 2025-06-29
 */
public interface UserService extends IService<User> {

    UserDto getUserInfo(Integer userId);

}
