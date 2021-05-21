package com.epochong.chatroom.application.command.service.impl;

import com.epochong.chatroom.application.command.service.MessageCommandService;
import com.epochong.chatroom.controller.dto.MessageDto;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wangchong.epochong
 * @date 2021/5/21 14:04
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class MessageCommandServiceImplTest {

    private MessageCommandService messageCommandService = new MessageCommandServiceImpl();

    @Test
    @Transactional
    @Rollback
    public void insertRobotFaq() {
        MessageDto messageDto = new MessageDto();
        messageDto.setFromUserId(28);
        messageDto.setToUserId(23);
        messageDto.setUserType(2);
        messageDto.setContent("小米");
        messageCommandService.insertRobotFaq(messageDto);
    }
}