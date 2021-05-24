package com.epochong.chatroom.application.query.service;

import com.epochong.chatroom.controller.dto.UserDto;
import com.epochong.chatroom.domian.value.BaseResp;

/**
 * @author wangchong.epochong
 * @date 2021/5/11 9:17
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public interface UserQueryService {

    /**
     * 用户登录
     * @param userDto
     * @return
     */
    BaseResp userLogin(UserDto userDto);

    /**
     * 根据userId查询User
     * @param userDto
     * @return
     */
    BaseResp queryUserById(UserDto userDto);
}
