package com.epochong.chatroom.application.command.service.impl;

import com.epochong.chatroom.application.command.service.UserCommandService;
import com.epochong.chatroom.controller.dto.LoginDto;
import com.epochong.chatroom.domian.entity.User;
import com.epochong.chatroom.domian.value.BaseResp;
import com.epochong.chatroom.exception.ResourceException;
import com.epochong.chatroom.infrastructure.repository.mapper.AccountMapper;

/**
 * @author wangchong.epochong
 * @date 2021/5/11 9:15
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class UserCommandServiceImpl implements UserCommandService {

    private AccountMapper accountMapper = new AccountMapper();

    @Override
    public BaseResp userRegister(LoginDto loginDto) {
        User user = new User();
        user.setUserName(loginDto.getUsername());
        user.setPassword(loginDto.getPassword());
        user.setUserType(loginDto.getUserType());
        return accountMapper.userRegister(user) ? new BaseResp(ResourceException.SUCCESS) : new BaseResp(ResourceException.REGISTER_FAIL);
    }
}