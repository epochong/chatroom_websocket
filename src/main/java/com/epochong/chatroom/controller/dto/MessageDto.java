package com.epochong.chatroom.controller.dto;

import lombok.Data;

/**
 * @author wangchong.epochong
 * @date 2021/5/18 10:58
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
@Data
public class MessageDto {
    private Integer fromUserId;
    private Integer toUserId;
    private String content;
    private Long createTime;
    private Integer userType;
    private Long id;
    private String fromUserName;
    private String toUserName;
    private Integer type;
}
