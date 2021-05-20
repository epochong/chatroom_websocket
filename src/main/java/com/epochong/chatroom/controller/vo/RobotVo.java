package com.epochong.chatroom.controller.vo;

import lombok.Data;

/**
 * @author wangchong.epochong
 * @date 2021/5/20 20:13
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
@Data
public class RobotVo {
    private Integer id;
    private Integer faqValid;
    private String faq;
    private String answer;
}
