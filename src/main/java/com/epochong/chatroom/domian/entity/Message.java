package com.epochong.chatroom.domian.entity;

import lombok.Data;

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
    private Integer toUserId;
    private String content;
    private Long createTime;
}
