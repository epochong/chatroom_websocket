package com.epochong.chatroom.application.query.service.impl;

import com.epochong.chatroom.application.query.service.UserQueryService;
import com.epochong.chatroom.controller.dto.LoginDto;
import com.epochong.chatroom.domian.entity.User;
import com.epochong.chatroom.domian.value.BaseResp;
import com.epochong.chatroom.exception.ResourceException;
import com.epochong.chatroom.infrastructure.repository.mapper.AccountMapper;
import com.epochong.chatroom.infrastructure.repository.utils.Constant;

/**
 * @author wangchong.epochong
 * @date 2021/5/11 9:18
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class UserQueryServiceImpl implements UserQueryService {

    private AccountMapper accountMapper = new AccountMapper();

    @Override
    public BaseResp userLogin(LoginDto loginDto) {
        User user = accountMapper.userLogin(loginDto);
        if (user == null) {
            return new BaseResp(ResourceException.ACCOUNT_NOT_CORRECT);
        }
        if (user.getUserType().equals(Constant.INT_TO_USER_TYPE) && loginDto.getUserType().equals(Constant.INT_FROM_USER_TYPE)) {
            return new BaseResp(ResourceException.NOT_FROM_USER);
        }
        if (user.getUserType().equals(Constant.INT_FROM_USER_TYPE) && loginDto.getUserType().equals(Constant.INT_TO_USER_TYPE)) {
            return new BaseResp(ResourceException.NOT_TO_USER);
        }
        BaseResp baseResp = new BaseResp(ResourceException.SUCCESS);
        baseResp.setObject(user);
        return baseResp;
    }
}
