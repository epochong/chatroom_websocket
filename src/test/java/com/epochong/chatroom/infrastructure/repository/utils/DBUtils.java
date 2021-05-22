package com.epochong.chatroom.infrastructure.repository.utils;

import com.epochong.chatroom.domian.entity.Message;
import com.epochong.chatroom.domian.entity.User;
import com.epochong.chatroom.infrastructure.repository.mapper.AccountMapper;
import com.epochong.chatroom.infrastructure.repository.mapper.MessageMapper;
import org.junit.Test;

import java.util.List;

/**
 * @author wangchong.epochong
 * @date 2021/5/22 17:38
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class DBUtils {

    private MessageMapper messageMapper = new MessageMapper();
    private AccountMapper accountMapper = new AccountMapper();

    @Test
    public void addUserNameToMessage() {
        Message message = new Message();
        message.setFromUserId(0);
        List <Message> messageList = messageMapper.queryByFromUserId(message);
        for (Message msg : messageList) {
            User user = new User();
            user.setId(msg.getFromUserId());
            User user1 = accountMapper.queryById(user);
            user.setId(msg.getToUserId());
            User user2 = accountMapper.queryById(user);
            if (msg.getContent().startsWith("机器")) {
                msg.setFromUserName("机器人" + User.UserType.NAMES.get(user1.getUserType()) + user1.getUserName());
            } else {
                msg.setFromUserName(User.UserType.NAMES.get(user1.getUserType()) + user1.getUserName());
            }
            msg.setToUserName(User.UserType.NAMES.get(user2.getUserType()) + user2.getUserName());
            messageMapper.updateUserName(msg);
        }
    }

    @Test
    public void setMessageType() {
        Message message = new Message();
        message.setFromUserId(0);
        List <Message> messageList = messageMapper.queryByFromUserId(message);
        for (Message msg : messageList) {
            if (msg.getContent().startsWith("客服")) {
                msg.setType(1);
            } else if (msg.getContent().startsWith("用户")) {
                msg.setType(2);
            } else if (msg.getContent().startsWith("机器")) {
                msg.setType(3);
            } else {
                msg.setType(3);
            }
            messageMapper.updateType(msg);
        }
    }
}
