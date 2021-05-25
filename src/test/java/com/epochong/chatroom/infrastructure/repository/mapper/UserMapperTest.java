package com.epochong.chatroom.infrastructure.repository.mapper;

import com.epochong.chatroom.controller.dto.UserDto;
import com.epochong.chatroom.domian.entity.User;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author epochong
 * @date 2019/8/3 10:20
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class UserMapperTest {
    private UserMapper userMapper = new UserMapper();

    /**
     * 检验注册功能
     */
    @Test
    public void userRegister() {

        User user = new User();
        user.setUserName("test3");
        user.setPassword("test3");
        user.setUserType(2);
        boolean isSuccess = userMapper.userRegister(user);
        Assert.assertEquals(true,isSuccess);
    }

    /**
     * 检验登录功能
     * 查询数据库看看是否有该用户
     */
    @Test
    public void userLogin() {
        UserDto userDto = new UserDto();
        userDto.setUsername("wangchong");
        userDto.setPassword("wangchong");
        userDto.setUserType(1);
        User user = userMapper.userLogin(userDto);
        Assert.assertNotNull(user);
    }
}