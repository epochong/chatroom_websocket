package com.epochong.chatroom.infrastructure.repository.dao;

import com.epochong.chatroom.domian.entity.User;
import com.epochong.chatroom.infrastructure.repository.AccountDao;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author epochong
 * @date 2019/8/3 10:20
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class AccountDaoTest {
    private AccountDao accountDao = new AccountDao();

    /**
     * 检验注册功能
     */
    @Test
    public void userRegister() {

        User user = new User();
        user.setUserName("test2");
        user.setPassword("test2");
        boolean isSuccess = accountDao.userRegister(user);
        Assert.assertEquals(true,isSuccess);
    }

    /**
     * 检验登录功能
     * 查询数据库看看是否有该用户
     */
    @Test
    public void userLogin() {
        User user = accountDao.userLogin("test2","test2");
        Assert.assertNotNull(user);
    }
}