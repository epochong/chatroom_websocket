package com.epochong.chatroom.application.query.service.impl;

import com.epochong.chatroom.application.query.service.AccountQueryService;
import com.epochong.chatroom.controller.dto.LoginDto;
import com.epochong.chatroom.domian.entity.User;
import com.epochong.chatroom.domian.value.BaseResp;
import com.epochong.chatroom.exception.ResourceException;
import com.epochong.chatroom.infrastructure.repository.AccountDao;
import com.epochong.chatroom.infrastructure.repository.utils.Constant;

/**
 * @author wangchong.epochong
 * @date 2021/5/11 9:18
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class AccountQueryServiceImpl implements AccountQueryService {

    private AccountDao accountDao = new AccountDao();

    @Override
    public BaseResp userLogin(LoginDto loginDto) {
        User user = accountDao.userLogin(loginDto.getUsername(), loginDto.getPassword());
        if (user == null) {
            return new BaseResp(ResourceException.ACCOUNT_NOT_CORRECT);
        }
        if (user.getUserType().equals(Constant.INT_TO_USER_TYPE) && loginDto.getUserType().equals(Constant.INT_FROM_USER_TYPE)) {
            return new BaseResp(ResourceException.NOT_FROM_USER);
        }
        if (user.getUserType().equals(Constant.INT_FROM_USER_TYPE) && loginDto.getUserType().equals(Constant.INT_TO_USER_TYPE)) {
            return new BaseResp(ResourceException.NOT_TO_USER);
        }
        return new BaseResp(ResourceException.SUCCESS);
    }
}
