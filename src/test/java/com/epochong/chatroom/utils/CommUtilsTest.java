package com.epochong.chatroom.utils;

import com.epochong.chatroom.entity.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

/**
 * @author epochong
 * @date 2019/7/30 19:12
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe 单元测试类(白盒测试)
 */
public class CommUtilsTest {


    @Test
    public void loadProperties() {
        String fileName = "datasource.properties";
        Properties properties = CommUtils.loadProperties(fileName);
        System.out.println(properties);
        String url = properties.getProperty("url");
        Assert.assertNotNull(null,url);
    }

    @Test
    public void gsonObjectToSerialize() {
        User user = new User();
        user.setId(10);
        user.setUserName("test");
        user.setPassword("123");
        String jsonStr = CommUtils.object2Json(user);
        Assert.assertNotNull(jsonStr);
    }

    @Test
    public void gsonSerializeToObject() {
        String jsonStr = "{\"id\":10,\"userName\":\"test\",\"password\":\"123\"}";
        User user = (User) CommUtils.json2Object(jsonStr,User.class);
        System.out.println(user);
        Assert.assertNotNull(user);
    }
}