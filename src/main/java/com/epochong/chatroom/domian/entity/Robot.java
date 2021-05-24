package com.epochong.chatroom.domian.entity;

import lombok.Data;

/**
 * @author wangchong.epochong
 * @date 2021/5/17 14:50
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
@Data
public class Robot {
    private Integer id;
    private Integer faqValid;
    private String faq;
    private String answer;
    private Integer matches;
}
