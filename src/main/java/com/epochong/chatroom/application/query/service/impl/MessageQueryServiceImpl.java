package com.epochong.chatroom.application.query.service.impl;

import com.epochong.chatroom.application.query.service.MessageQueryService;
import com.epochong.chatroom.controller.assember.MessageAssembler;
import com.epochong.chatroom.controller.dto.MessageDto;
import com.epochong.chatroom.domian.entity.Message;
import com.epochong.chatroom.domian.value.BaseResp;
import com.epochong.chatroom.exception.ResourceException;
import com.epochong.chatroom.infrastructure.repository.mapper.MessageMapper;
import com.epochong.chatroom.infrastructure.repository.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author wangchong.epochong
 * @date 2021/5/18 12:46
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
@Slf4j
public class MessageQueryServiceImpl implements MessageQueryService {

    private MessageMapper messageMapper = new MessageMapper();

    @Override
    public BaseResp queryMessageByUserId(MessageDto messageDto) {
        try {
            BaseResp baseResp = BaseResp.getSuccessResp();
            List <Message> messages;
            if (messageDto.getUserType() == Constant.INT_FROM_USER_TYPE) {
                messages = messageMapper.queryByFromUserId(MessageAssembler.getMessage(messageDto));
            } else {
                messages = messageMapper.queryByToUserId(MessageAssembler.getMessage(messageDto));
            }
            if (!CollectionUtils.isEmpty(messages)) {
                baseResp.setObject(messages);
                return baseResp;
            }
        } catch (Exception e) {
            log.error("queryMessageByUserId() error:{}", ExceptionUtils.getStackTrace(e));
        }
        return BaseResp.getFailResp();
    }

    @Override
    public BaseResp queryLastMessageByUserId(MessageDto messageDto) {
        BaseResp baseResp;
        try {
            Message message = messageMapper.queryLastMessageByToUserId(MessageAssembler.getMessage(messageDto));
            if (Objects.isNull(message.getFromUserId())) {
                baseResp = new BaseResp(ResourceException.HAVE_NO_MESSAGE);
                return baseResp;
            }
            baseResp = BaseResp.getSuccessResp();
            baseResp.setObject(message);
        } catch (Exception e) {
            log.error("error(): {}", ExceptionUtils.getStackTrace(e));
            return BaseResp.getFailResp();
        }
        return baseResp;
    }
}
