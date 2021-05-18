package com.epochong.chatroom;

import com.baidu.aip.nlp.AipNlp;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangchong.epochong
 * @date 2021/5/16 22:55
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class MainTest {
    //设置APPID/AK/SK
    public static final String APP_ID = "24180986";
    public static final String API_KEY = "jBLM29MLfOiTHARznldttF7S";
    public static final String SECRET_KEY = "QRmV6iOSI6aWW6Q6m6IQqqozamy4bhrk";

    public static void main(String[] args) {
        // 初始化一个AipNlp
        AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 调用接口
        String text = "华为手机有ffdee什么pric";
        JSONObject res = client.lexer(text, null);
        System.out.println(res.toString(2));
        JSONArray items = (JSONArray) res.get("items");
        System.out.println(items.get(0));
        System.out.println(items.get(1));

    }

    @Test
    public void testSortMap() {
        Map<Integer, Integer> map = new HashMap <>();
        map.put(1,1);
        map.put(0,2);
        map.put(3,-1);
        Map.Entry <Integer, Integer> entry = map.entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue)).get();
        System.out.println(entry.getKey());

    }

    @Test
    public void regxChinese(){
        String str = "小米 手机。。。..''0976;;,,//.啦啦啦啦sllssl解决就Joi";
        String str1 = "小米";
        System.out.println(str1.substring(0,2));
        System.out.println(str.replaceAll("\\s+", "").replaceAll("[\\pP\\p{Punct}]",""));
        System.out.println(str);

        Pattern pat = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher mat = pat.matcher(str1);
        System.out.println(mat.matches());
        String result = mat.replaceAll("");
      //  System.out.println(result);
    }

}
