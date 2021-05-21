package com.epochong.chatroom.application.command.service.impl;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import com.epochong.chatroom.application.command.service.RobotCommandService;
import com.epochong.chatroom.controller.dto.RobotDto;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author wangchong.epochong
 * @date 2021/5/21 14:05
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class RobotCommandServiceImplTest {

    private RobotCommandService robotCommandService = new RobotCommandServiceImpl();


    @Test
    @Rollback
    public void insertRobotFaq() {
        RobotDto dto = new RobotDto();
        dto.setFaq("test");
        dto.setAnswer("test");
        dto.setFaqValid(1);
        robotCommandService.insertRobotFaq(dto);
    }

    @Test
    @Rollback
    public void updateById() {
        RobotDto dto = new RobotDto();
        dto.setId(1);
        dto.setFaq("test");
        dto.setFaqValid(1);
        dto.setAnswer("test");
        robotCommandService.updateById(dto);
    }

    @Test
    @Transactional
    @Rollback
    public void deleteById() {
        RobotDto dto = new RobotDto();
        dto.setId(1);
        robotCommandService.deleteById(dto);
    }
}