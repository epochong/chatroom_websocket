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
    /**
     * 根据userId搜索所有历史消息
     * @param messageDto
     * @return
     */
    BaseResp queryMessageByUserId(MessageDto messageDto) ;

    /**
     * 根据用户userId搜索最后一条消息所对应的客服
     * @param messageDto
     * @return
     */
    BaseResp queryLastMessageByUserId(MessageDto messageDto);
}
