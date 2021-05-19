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
     * 数字
     */
    Double MOST_LIKE_PERCENT = 0.8;
    Double ONE_DOUBLE = 1.0;
    /**
     * 参数字段名
     */
    String ITEM = "item";
    String ITEMS = "items";
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
    String ROBOT_USER_TYPE = "3";
    int INT_FROM_USER_TYPE = 1;
    int INT_TO_USER_TYPE = 2;
    Integer INT_ROBOT_USER_TYPE = 3;
    /**
     * 聊天类型
     */
    String GROUP_CHAT_TYPE = "1";
    String PRIVATE_CHAT_TYPE = "2";
    /**
     * code
     */
    Integer SUCCESS = 10000;
    /**
     * 机器人问题是否生效
     */
    Integer FAQ_VALID = 1;
    Integer FAQ_INVALID = 2;
    /**
     * 名称
     */
    String ROBOT_NAME = "机器人";
    String FROM_USER_NAME = "客服";
    String TO_USER_NAME = "用户";
    /**
     * 话术
     */
    String ROBOT_MAYBE_ANSWER = "您可能想问的问题是:";
    String ROBOT_MAYBE_ANSWER2 = "对应回复为:";
    String NEW_LINE = "<br/>";
}
