package com.epochong.chatroom.domian.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangchong.epochong
 * @date 2021/5/18 10:40
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
@Data
public class Message {
    private Long id;
    private Integer fromUserId;
    private String fromUserName;
    private Integer toUserId;
    private String toUserName;
    private String content;
    private Long createTime;
    private Integer type;

    public interface Type {
        Map<String, String> NAMES = new HashMap<String, String>() {{
            String FROM_USER = "客服";
            String TO_USER = "用户";
            String ROBOT = "机器人";

            put(FROM_USER, "1");
            put(TO_USER, "2");
            put(ROBOT, "3");
        }};
    }
}
