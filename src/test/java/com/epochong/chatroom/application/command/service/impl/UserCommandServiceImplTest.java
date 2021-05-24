package com.epochong.chatroom.application.command.service.impl;

import com.epochong.chatroom.application.command.service.UserCommandService;
import com.epochong.chatroom.controller.dto.UserDto;
import org.junit.Test;

/**
 * @author wangchong.epochong
 * @date 2021/5/21 14:04
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class UserCommandServiceImplTest {

    private UserCommandService userCommandService = new UserCommandServiceImpl();

    @Test
    public void userRegister() {
        UserDto dto = new UserDto();
        dto.setUsername("test1");
        dto.setPassword("test1");
        dto.setUserType(1);
        userCommandService.userRegister(dto);
    }
}