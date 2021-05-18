package com.epochong.chatroom.application.command.service;

import com.epochong.chatroom.controller.dto.RobotDto;
import com.epochong.chatroom.domian.value.BaseResp;

/**
 * @author wangchong.epochong
 * @date 2021/5/17 15:24
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public interface RobotCommandService {
    /**
     * 用户注册
     * @param robotDto 机器人问题回复
     * @return
     */
    BaseResp insertRobotFaq(RobotDto robotDto) ;
}
