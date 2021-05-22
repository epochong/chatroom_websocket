package com.epochong.chatroom.domian.entity;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author wangchong.epochong
 * @date 2021/5/22 14:51
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
@Data
public class Router {
    /**
     * 优先级1：熟人客服
     */
    User recentFromUser;
    /**
     * 优先级2：如果没有熟人客服按照城市维度分配所有该城市的客服
     */
    String city;
    /**
     * 已上线的客服列表
     */
    List<User> onlineList;


    public List<User> getFromUserList() {
        if (Objects.nonNull(recentFromUser)) {

        }
        //
        return null;
    }
}
