package com.epochong.chatroom.application.query.service;

import com.epochong.chatroom.controller.dto.RobotDto;
import com.epochong.chatroom.domian.value.BaseResp;

/**
 * @author wangchong.epochong
 * @date 2021/5/17 15:53
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public interface RobotQueryService {
    BaseResp queryAnswer(RobotDto dto);
}
