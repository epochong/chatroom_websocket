package com.epochong.chatroom.application.query.service.impl;

import com.epochong.chatroom.application.query.service.UserQueryService;
import com.epochong.chatroom.controller.dto.LoginDto;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author wangchong.epochong
 * @date 2021/5/21 14:05
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class UserQueryServiceImplTest {

    private UserQueryService userQueryService = new UserQueryServiceImpl();

    @Test
    public void userLogin() {
        LoginDto dto = new LoginDto();
        dto.setUsername("wangchong");
        dto.setPassword("wangchong");
        dto.setUserType(1);
        userQueryService.userLogin(dto);
    }
}