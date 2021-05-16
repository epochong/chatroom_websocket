package com.epochong.chatroom.application.query.service;

import com.epochong.chatroom.controller.dto.LoginDto;
import com.epochong.chatroom.domian.value.BaseResp;

/**
 * @author wangchong.epochong
 * @date 2021/5/11 9:17
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public interface AccountQueryService {

    /**
     * 用户登录
     * @param loginDto
     * @return
     */
    BaseResp userLogin(LoginDto loginDto);
}
