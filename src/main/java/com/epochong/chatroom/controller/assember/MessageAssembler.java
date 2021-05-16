package com.epochong.chatroom.controller.assember;

import com.epochong.chatroom.application.websocket.WebSocketService;
import com.epochong.chatroom.domian.value.Message2Client;
import com.epochong.chatroom.domian.value.MessageFromClient;

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
        message2Client.setUserType(messageFromClient.getFromUserType());
        message2Client.setNames(WebSocketService.names);
        return message2Client;
    }

    public static Message2Client getMessage2Client(MessageFromClient messageFromClient, String userName) {
        //发送私聊信息
        Message2Client message2Client = new Message2Client();
        //发送信息的用户说的内容
        message2Client.setContent(userName, messageFromClient.getMsg(), messageFromClient.getFromUserType());
        message2Client.setUserType(messageFromClient.getFromUserType());
        //通过names在前端通过key(SessionID)找到对应的发送者的名字,从而得以显示
        message2Client.setNames(WebSocketService.names);
        return message2Client;
    }
}
