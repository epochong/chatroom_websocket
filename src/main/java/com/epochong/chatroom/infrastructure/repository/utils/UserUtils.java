package com.epochong.chatroom.infrastructure.repository.utils;

import com.epochong.chatroom.domian.entity.User;

/**
 * @author wangchong.epochong
 * @date 2021/5/10 0:10
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class UserUtils {
    public static String getUserName(String userName, Integer userType) {
        return User.UserType.NAMES.get(userType) + userName;
    }
}
