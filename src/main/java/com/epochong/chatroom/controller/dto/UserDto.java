package com.epochong.chatroom.controller.dto;

import lombok.Data;

/**
 * @author wangchong.epochong
 * @date 2021/5/8 22:17
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
@Data
public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private Integer userType;
    private String city;
}
