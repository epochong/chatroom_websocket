package com.epochong.chatroom.application.query.service.impl;

import com.epochong.chatroom.application.query.service.RobotQueryService;
import com.epochong.chatroom.controller.dto.RobotDto;
import com.epochong.chatroom.domian.entity.Robot;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author wangchong.epochong
 * @date 2021/5/21 14:05
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class RobotQueryServiceImplTest {

    private RobotQueryService robotQueryService = new RobotQueryServiceImpl();

    @Test
    public void queryAnswer() {
        RobotDto dto = new RobotDto();
        dto.setFaq("小米");
        robotQueryService.queryAnswer(dto);
    }

    @Test
    public void queryAnswerById() {
        RobotDto dto = new RobotDto();
        dto.setId(1);
        robotQueryService.queryAnswerById(dto);
    }

    @Test
    public void getAll() {
        robotQueryService.getAll();
    }

    @Test
    public void searchByFaq() {
        RobotDto dto = new RobotDto();
        dto.setFaq("小米");
        robotQueryService.searchByFaq(dto);
    }
}