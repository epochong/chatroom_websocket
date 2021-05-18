package com.epochong.chatroom.domian.value;

import com.epochong.chatroom.exception.ResourceException;
import lombok.Data;

/**
 * @author wangchong.epochong
 * @date 2021/5/11 10:46
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
@Data
public class BaseResp {
    int code;
    String message;
    Object object;

    public BaseResp() {
    }

    public BaseResp(ResourceException resource) {
        this.code = resource.getCode();
        this.message = resource.getMessage();
    }

    public static BaseResp getSuccessResp() {
        BaseResp baseResp = new BaseResp(ResourceException.SUCCESS);
        return baseResp;
    }

    public static BaseResp getFailResp() {
        BaseResp baseResp = new BaseResp(ResourceException.SYSTEM_ERROR);
        return baseResp;
    }
}
