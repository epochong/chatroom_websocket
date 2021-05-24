package com.epochong.chatroom.application.command.service;

import com.epochong.chatroom.controller.dto.UserDto;
import com.epochong.chatroom.domian.value.BaseResp;

/**
 * @author epochong
 * @date 2019/8/3 10:38
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public interface UserCommandService {

    /**
     * 用户注册
     * @param userDto 注册对象信息
     * @return
     */
    BaseResp userRegister(UserDto userDto) ;
}
