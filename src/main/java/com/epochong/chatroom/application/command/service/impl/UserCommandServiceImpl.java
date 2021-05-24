package com.epochong.chatroom.application.command.service.impl;

import com.epochong.chatroom.application.command.service.UserCommandService;
import com.epochong.chatroom.controller.dto.UserDto;
import com.epochong.chatroom.domian.entity.User;
import com.epochong.chatroom.domian.value.BaseResp;
import com.epochong.chatroom.exception.ResourceException;
import com.epochong.chatroom.infrastructure.repository.mapper.UserMapper;

/**
 * @author wangchong.epochong
 * @date 2021/5/11 9:15
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class UserCommandServiceImpl implements UserCommandService {

    private UserMapper userMapper = new UserMapper();

    @Override
    public BaseResp userRegister(UserDto userDto) {
        User user = new User();
        user.setUserName(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setUserType(userDto.getUserType());
        return userMapper.userRegister(user) ? new BaseResp(ResourceException.SUCCESS) : new BaseResp(ResourceException.REGISTER_FAIL);
    }
}
