package com.epochong.chatroom.infrastructure.repository.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author epochong
 * @date 2019/7/30 19:05
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe 封装基础的工具方法
 */
@Slf4j
public class CommUtils {
    /**
     * 创建Gson对象
     * 将字符串序列化和反序列化
     */
    private static final Gson gson = new GsonBuilder().create();
    private CommUtils(){}

    /**
     * 根据指定的文件名加载配置文件
     * @param fileName 配置文件名
     * @return
     */
    public static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        // 获取当前配置文件夹下的文件输入流
        InputStream in = CommUtils.class.getClassLoader()
                .getResourceAsStream(fileName);
        // 加载配置文件中的所有内容
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 将对象序列化Json字符串
     * @param obj
     * @return
     */
    public static String object2Json(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * 将Json字符串反序列化成对象
     * @param jsonStr
     * @param objClass
     * @return
     */
    public static Object json2Object(String jsonStr,Class objClass) {
        return gson.fromJson(jsonStr,objClass);
    }

    public static Map<String, String> getUrlParams(String urlParams) {
        String[] params = urlParams.split(Constant.SYMBOL_AND);
        Map<String, String> resMap = new HashMap <>();
        for (String param : params) {
            String[] keyValue = param.split(Constant.SYMBOL_EQUAL);
            try {
                resMap.put(keyValue[0], URLDecoder.decode(keyValue[1], "utf-8"));
            } catch (UnsupportedEncodingException e) {
                log.error("getUrlParams(): decode转换异常 error:{}", ExceptionUtils.getStackTrace(e));
            }
        }
        return resMap;
    }
}
