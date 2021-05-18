package com.epochong.chatroom.controller.dto;

import lombok.Data;

import java.util.List;

/**
 * @author wangchong.epochong
 * @date 2021/5/17 15:25
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
@Data
public class RobotDto {
    private List<String> keyWords;
    private String faq;
    private String answer;
    private Integer faqValid;
}
