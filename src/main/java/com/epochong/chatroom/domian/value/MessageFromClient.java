package com.epochong.chatroom.domian.value;

import lombok.Data;

/**
 * @author epochong
 * @date 2019/8/6 11:30
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe 前端发送给后端的信息实体类
 * 群聊:{"msg":"777","type":1}
* 私聊:{"to":"0-","msg":"33333","type":2}
 */
@Data
public class MessageFromClient {
    //聊天信息
    private String msg;
    //聊天类别：1表示群聊，2表示私聊
    private String type;
    //私聊的对象SessionID
    private String to;
    //发送者类型：1-客服,2-用户
    private String fromUserType;
}
