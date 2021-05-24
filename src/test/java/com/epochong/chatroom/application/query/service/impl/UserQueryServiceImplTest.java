package com.epochong.chatroom.application.query.service.impl;

import com.epochong.chatroom.application.query.service.UserQueryService;
import com.epochong.chatroom.controller.dto.UserDto;
import org.junit.Test;

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
        UserDto dto = new UserDto();
        dto.setUsername("wangchong");
        dto.setPassword("wangchong");
        dto.setUserType(1);
        userQueryService.userLogin(dto);
    }
}