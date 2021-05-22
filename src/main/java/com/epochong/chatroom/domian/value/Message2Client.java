package com.epochong.chatroom.domian.value;

import lombok.Data;

import java.util.HashMap;
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
    private Map<String, String> names;
    /**
     * 用户类型，用于区分客服还是用户
     */
    private String userType;
    /**
     * 消息类型
     */
    private Integer messageType;
    /**
     * 消息时间
     */
    private String messageTime;

    /**
     * 发送者调用
     * @param userName 发送信息的用户的名称
     * @param msg 私聊的信息
     */
    public void setContent(String userName,String msg, String userType) {
        this.content = userName + "说：" + msg;
    }

    public interface MessageType {
        Map<Integer, String> NAMES = new HashMap<Integer, String>() {{
            int AUTO = 1;
            int SELF = 2;

            put(AUTO, "自动回复");
            put(SELF, "自主发送");
        }};
    }

    public interface UserType {
        Map<String, String> NAMES = new HashMap<String, String>() {{
            String FROM_USER = "客服";
            String TO_USER = "用户";
            String ROBOT = "机器";

            put(FROM_USER, "1");
            put(TO_USER, "2");
            put(ROBOT, "3");
        }};
    }
}
