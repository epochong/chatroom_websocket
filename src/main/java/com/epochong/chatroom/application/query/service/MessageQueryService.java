package com.epochong.chatroom.application.query.service;

import com.epochong.chatroom.controller.dto.MessageDto;
import com.epochong.chatroom.domian.entity.Message;
import com.epochong.chatroom.domian.value.BaseResp;

/**
 * @author wangchong.epochong
 * @date 2021/5/18 10:57
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public interface MessageQueryService {
    BaseResp queryMessageByUserId(MessageDto messageDto) ;

}
