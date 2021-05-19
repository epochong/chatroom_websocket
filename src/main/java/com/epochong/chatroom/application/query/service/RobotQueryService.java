package com.epochong.chatroom.application.query.service;

import com.epochong.chatroom.controller.dto.RobotDto;
import com.epochong.chatroom.domian.entity.Robot;
import com.epochong.chatroom.domian.value.BaseResp;

import java.util.List;

/**
 * @author wangchong.epochong
 * @date 2021/5/17 15:53
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public interface RobotQueryService {
    BaseResp queryAnswer(RobotDto dto);
    BaseResp queryAnswerById(RobotDto dto);
    List<Robot> getAll();
    List<Robot> searchByFaq(RobotDto dto);
}
