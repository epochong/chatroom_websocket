package com.epochong.chatroom.infrastructure.repository.mapper;

import com.epochong.chatroom.controller.dto.LoginDto;
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
public class AccountMapperTest {
    private AccountMapper accountMapper = new AccountMapper();

    /**
     * 检验注册功能
     */
    @Test
    public void userRegister() {

        User user = new User();
        user.setUserName("test2");
        user.setPassword("test2");
        boolean isSuccess = accountMapper.userRegister(user);
        Assert.assertEquals(true,isSuccess);
    }

    /**
     * 检验登录功能
     * 查询数据库看看是否有该用户
     */
    @Test
    public void userLogin() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("test");
        loginDto.setPassword("test");
        User user = accountMapper.userLogin(loginDto);
        Assert.assertNotNull(user);
    }
}