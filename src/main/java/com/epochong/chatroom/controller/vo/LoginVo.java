package com.epochong.chatroom.controller.vo;

import lombok.Data;

/**
 * @author wangchong.epochong
 * @date 2021/5/8 22:07
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
@Data
public class LoginVo {
    private String username;
    private String password;
    private Integer userType;
}
