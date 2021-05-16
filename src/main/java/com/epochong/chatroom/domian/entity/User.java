package com.epochong.chatroom.domian.entity;


import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author epochong
 * @date 2019/7/30 21:22
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe Json序列化
 * user表的实体类，数据库查询返回封装为User
 */
@Data
public class User {
    /**
     * 不设置id的值默认是0，如果不设置值就会和数据库中的数据有冲突
     * 涉及基本类型都使用包装类，默认为null
     * private transient Integer id;
     * transient：该属性不被序列化到Json字符串
     */
    private transient Integer id;
    private String userName;
    private String password;
    private Integer userType;

    public interface UserType {
        Map<Integer, String> NAMES = new HashMap<Integer, String>() {{
            int FROM_USER = 1;
            int TO_USER = 2;

            put(FROM_USER, "客服");
            put(TO_USER, "用户");
        }};
    }
}
