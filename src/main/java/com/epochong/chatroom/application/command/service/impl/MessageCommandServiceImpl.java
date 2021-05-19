package com.epochong.chatroom.application.command.service.impl;

import com.epochong.chatroom.application.command.service.MessageCommandService;
import com.epochong.chatroom.controller.assember.MessageAssembler;
import com.epochong.chatroom.controller.dto.MessageDto;
import com.epochong.chatroom.domian.entity.Message;
import com.epochong.chatroom.domian.value.BaseResp;
import com.epochong.chatroom.infrastructure.repository.dao.MessageDao;

/**
 * @author wangchong.epochong
 * @date 2021/5/18 11:01
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class MessageCommandServiceImpl implements MessageCommandService {

    private MessageDao messageDao = new MessageDao();
    @Override
    public BaseResp insertRobotFaq(MessageDto messageDto) {
        Message message = MessageAssembler.getMessage(messageDto);
        boolean isSuccess = messageDao.insertMessage(message);
        if (isSuccess) {
            return BaseResp.getSuccessResp();
        }
        return BaseResp.getFailResp();
    }
}