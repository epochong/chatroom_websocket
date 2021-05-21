package com.epochong.chatroom.application.query.service.impl;

import com.epochong.chatroom.application.query.service.MessageQueryService;
import com.epochong.chatroom.controller.dto.MessageDto;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author wangchong.epochong
 * @date 2021/5/21 14:05
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class MessageQueryServiceImplTest {

    private MessageQueryService messageQueryService = new MessageQueryServiceImpl();

    @Test
    public void queryMessageByUserId() {
        MessageDto messageDto = new MessageDto();
        messageDto.setFromUserId(28);
        messageQueryService.queryMessageByUserId(messageDto);

    }
}