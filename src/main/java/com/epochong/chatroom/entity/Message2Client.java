package com.epochong.chatroom.entity;

import lombok.Data;

import java.util.Map;

/**
 * @author epochong
 * @date 2019/8/6 11:26
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe 后端发送给前端的信息实体
 */
@Data
public class Message2Client {
    /**
     * 聊天内容
     */
    private String content;
    /**
     * 服务端登录的所有用户列表
     */
    private Map<String,String> names;

    public String getContent() {
        return content;
    }

    public Map <String, String> getNames() {
        return names;
    }

    public void setNames(Map <String, String> names) {
        this.names = names;
    }

    public void setContent(String msg) {
        this.content = msg;
    }

    /**
     * 发送者调用
     * @param userName 发送信息的用户的名称
     * @param msg 私聊的信息
     */
    public void setContent(String userName,String msg) {
        this.content = userName + "说：" + msg;
    }
}
