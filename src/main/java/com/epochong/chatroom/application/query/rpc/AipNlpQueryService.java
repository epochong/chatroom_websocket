package com.epochong.chatroom.application.query.rpc;

import java.util.List;

/**
 * @author wangchong.epochong
 * @date 2021/5/17 18:00
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public interface AipNlpQueryService {
    List<String> getStringSplit(String text);
}
