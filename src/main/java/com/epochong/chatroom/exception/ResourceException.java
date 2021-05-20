package com.epochong.chatroom.exception;

/**
 * @author wangchong.epochong
 * @date 2021/5/11 10:30
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public enum  ResourceException {

    SUCCESS(10000,"请求成功"),
    NOT_FROM_USER(10001, "您不是客服，请使用普通用户登录！"),
    NOT_TO_USER(10002, "您是客服，请使用客服身份登录！"),
    ACCOUNT_NOT_CORRECT(10003, "账户或密码不正确！"),
    REGISTER_FAIL(10004,"注册失败！"),
    ROBOT_LAST_MESSAGE(20001, "这个问题机器人还没学会，请人工回复！"),
    SYSTEM_ERROR(00000, "系统异常，请联系管理员");

    private int code;
    private String message;

    ResourceException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
