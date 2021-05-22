package com.epochong.chatroom.infrastructure.repository.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangchong.epochong
 * @date 2021/5/22 12:33
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class TimeUtils {
    public static String timestampToStr(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(timestamp));
    }
}
