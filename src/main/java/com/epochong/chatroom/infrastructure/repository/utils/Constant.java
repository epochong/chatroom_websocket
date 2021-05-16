package com.epochong.chatroom.infrastructure.repository.utils;

/**
 * @author wangchong.epochong
 * @date 2021/5/9 18:00
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public interface Constant {
    /**
     * 符号
     */
    String SYMBOL_EQUAL = "=";
    String SYMBOL_AND = "&";

    /**
     * 参数key
     */
    String USERNAME = "username";
    String USER_TYPE = "userType";
    /**
     * userType
     */
    String FROM_USER_TYPE = "1";
    String TO_USER_TYPE = "2";
    Integer INT_FROM_USER_TYPE = 1;
    Integer INT_TO_USER_TYPE = 2;
    /**
     * 聊天类型
     */
    String GROUP_CHAT_TYPE = "1";
    String PRIVATE_CHAT_TYPE = "2";
    /**
     * code
     */
    Integer SUCCESS = 10000;
}
