package com.epochong.chatroom.domian.entity;

import com.epochong.chatroom.application.websocket.WebSocketService;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
     * 优先级1：熟人客服+自己
     */
    List<WebSocketService> recent;
    /**
     * 优先级2：如果没有熟人客服按照城市维度分配所有该城市的客服
     */
    String city;
    /**
     * 已上线的客服列表
     */
    List<WebSocketService> onlineList;

    public Router() {
        this.onlineList = new ArrayList <>();
    }

    public List<WebSocketService> getFromUserList() {
        if (Objects.nonNull(recent)) {
            recent = new ArrayList <>();
            return recent;
        }
        // 如果没有熟人，就分配该城市的所有客服
        List <WebSocketService> filterCity = onlineList.stream()
                .filter(online -> online.getUser().getCity().equals(city))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(filterCity)) {
            return filterCity;
        }
        // 如果本城市的客服也没有将其他城市的上线了的客服分配出去
        return onlineList;
    }
}
