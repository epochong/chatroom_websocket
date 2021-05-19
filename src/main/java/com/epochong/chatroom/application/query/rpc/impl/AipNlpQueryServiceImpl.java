package com.epochong.chatroom.application.query.rpc.impl;

import com.baidu.aip.nlp.AipNlp;
import com.epochong.chatroom.application.query.rpc.AipNlpQueryService;
import com.epochong.chatroom.infrastructure.repository.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wangchong.epochong
 * @date 2021/5/17 18:04
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
@Slf4j
public class AipNlpQueryServiceImpl implements AipNlpQueryService {
    //设置APPID/AK/SK
    public static final String APP_ID = "24180986";
    public static final String API_KEY = "jBLM29MLfOiTHARznldttF7S";
    public static final String SECRET_KEY = "QRmV6iOSI6aWW6Q6m6IQqqozamy4bhrk";

    @Override
    public List<String> getStringSplit(String text) {
        JSONObject res = null;
        if (StringUtils.isEmpty(text)) {
            return Collections.emptyList();
        }
        List<String> splitList = new ArrayList <>();
        try {
            // 初始化一个AipNlp
            AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);

            // 可选：设置网络连接参数
            client.setConnectionTimeoutInMillis(2000);
            client.setSocketTimeoutInMillis(60000);
            // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
            //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
            //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
            // 去除text的所有字符和空格，防止影响分析
            text = text.replaceAll("\\s+", "").replaceAll("[\\pP\\p{Punct}]","");
            // 调用接口
            log.info("分词rpc调用参数：text:{}", text);
            res = client.lexer(text, null);
            log.info("分词rpc返回结果：{}", res.toString(2));
            JSONArray items = (JSONArray) res.get(Constant.ITEMS);
            for (Object item : items) {
                JSONObject object = (JSONObject) item;
                splitList.add((String) object.get(Constant.ITEM));
            }
        } catch (JSONException e) {
            log.error("error:{}", ExceptionUtils.getStackTrace(e));
            return Collections.emptyList();
        }
        return splitList;
    }
}
