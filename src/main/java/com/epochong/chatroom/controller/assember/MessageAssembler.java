package com.epochong.chatroom.controller.assember;

import com.epochong.chatroom.application.websocket.WebSocketService;
import com.epochong.chatroom.controller.dto.MessageDto;
import com.epochong.chatroom.domian.entity.Message;
import com.epochong.chatroom.domian.value.Message2Client;
import com.epochong.chatroom.domian.value.MessageFromClient;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author wangchong.epochong
 * @date 2021/5/12 12:46
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class MessageAssembler {
    public static Message2Client getMessage2Client(MessageFromClient messageFromClient) {
        Message2Client message2Client = new Message2Client();
        message2Client.setContent(messageFromClient.getMsg());
        message2Client.setNames(WebSocketService.names);
        message2Client.setType(Integer.parseInt(messageFromClient.getFromUserType()));
        message2Client.setTitleName(messageFromClient.getTitleName());
        return message2Client;
    }

    public static Message getMessage(MessageDto messageDto) {
        Message message= new Message();
        message.setFromUserId(messageDto.getFromUserId());
        message.setToUserId(messageDto.getToUserId());
        message.setContent(messageDto.getContent());
        message.setFromUserName(messageDto.getFromUserName());
        message.setToUserName(messageDto.getToUserName());
        message.setType(messageDto.getType());
        return message;
    }

    public static Message getMessage(ResultSet resultSet) throws SQLException {
        Message message= new Message();
        message.setId(resultSet.getLong("id"));
        message.setContent(resultSet.getString("content"));
        message.setFromUserId(resultSet.getInt("from_user_id"));
        message.setToUserId(resultSet.getInt("to_user_id"));
        message.setCreateTime(resultSet.getLong("create_time"));
        message.setFromUserName(resultSet.getString("from_user_name"));
        message.setToUserName(resultSet.getString("to_user_name"));
        message.setType(resultSet.getInt("type"));
        return message;
    }
}
