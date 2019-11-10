package com.epochong.chatroom.service;

import com.epochong.chatroom.entity.Message2Client;
import com.epochong.chatroom.entity.MessageFromClient;
import com.epochong.chatroom.utils.CommUtils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author epochong
 * @date 2019/8/3 11:54
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe 后端实现，ws协议
 * ServerEndpoint("/websocket")注解：把当前类标记为websocket类，ws协议的终端，和Servlet差不多
 */

@ServerEndpoint("/websocket")
public class WebSocket {
    /**
     * 存储所有连接到后端的websocket每个对象都有这个集合，Tomcat每次打开一个页面就重新建立一个对象
     */
    private static CopyOnWriteArraySet<WebSocket> clients = new CopyOnWriteArraySet <>();

    /**
     * 缓存所有的用户映射信息
     * session id --> 用户名
     */
    private static Map<String,String> names = new ConcurrentHashMap <>();
    /**
     * 绑定当前websocket会话
     * 每个浏览器窗口建立起来就是一个session会话
     */
    private Session session;

    /**
     * 当前客户端的用户名
     */
    private String userName;

    /**
     * 当前端websocket建立连接就会调用这个方法
     * 添加session
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        //绑定当前session
        this.session = session;
        // username=' + '${username}
        String userName = session.getQueryString().split("=")[1];
        this.userName = userName;
        //将客户端聊天实体保存到clients
        clients.add(this);
        //将当前用户以及SessionID保存到用户列表
        names.put(session.getId(),userName);
        System.out.println("有新的连接，SessionID为" + session.getId() + ",用户名为" + userName);
        //发送给所有在线用户一个上线通知
        Message2Client message2Client = new Message2Client();
        message2Client.setContent(userName + "上线了");
        message2Client.setNames(names);
        //发送信息
        String jsonStr = CommUtils.object2Json(message2Client);
        for (WebSocket websocket :
                clients) {
            websocket.sendMsg(jsonStr);
        }
    }

    @OnError
    public void onError(Throwable e) {
        System.out.println("WebSocket连接失败");
    }

    /**
     * 服务器收到了浏览器发送的信息
     * 群聊:{"msg":"777","type":1}
     * 私聊:{"to":"0-","msg":"33333","type":2}
     */
    @OnMessage
    public void onMessage(String msg) {
        /**
         *  msg -> MessageFromClient
         *  将前端发送的序列化转换成对象
         */
        MessageFromClient messageFromClient = (MessageFromClient) CommUtils.json2Object(msg,MessageFromClient.class);
        //1代表群聊
        if (messageFromClient.getType().equals(1)) {
            //群聊的信息
            String content = messageFromClient.getMsg();
            Message2Client message2Client = new Message2Client();
            message2Client.setContent(content);
            message2Client.setNames(names);
            //广播发送
            for (WebSocket webSocket :
                    clients) {
                webSocket.sendMsg(CommUtils.object2Json(message2Client));
            }
        }
        //2：私聊信息
        else if (messageFromClient.getType().equals("2")) {
            // {"to":"0-1-2-","msg":"33333","type":2}
            // 私聊内容
            String content = messageFromClient.getMsg();
            //to:私聊对象的SessionID
            int toL = messageFromClient.getTo().length();
            String tos[] = messageFromClient.getTo().substring(0,toL - 1).split("-");
            List<String> lists = Arrays.asList(tos);
            //给指定的SessionID发送信息 ，SessionID从0开始，新建一个窗口加1
            for (WebSocket webSocket :
                    clients) {
                //在所有客户存在的客户中找到了勾选的私聊对象（通过ID）并且不是自己
                if (lists.contains(webSocket.session.getId()) &&
                        !this.session.getId().equals(webSocket.session.getId())) {
                    //发送私聊信息
                    Message2Client message2Client = new Message2Client();
                    //发送信息的用户说的内容
                    message2Client.setContent(userName,content);
                    //通过names在前端通过key(SessionID)找到对应的发送者的名字,从而得以显示
                    message2Client.setNames(names);
                    webSocket.sendMsg(CommUtils.object2Json(message2Client));
                }
            }
        }
    }

    @OnClose
    public void onClose() {
        // 将客户端聊天实体移除
        clients.remove(this);
        // 将当前用户以及SessionID移除用户列表
        names.remove(session.getId());
        System.out.println("有连接下线了" + ",用户名为" + userName);
        Message2Client message2Client = new Message2Client();
        message2Client.setContent(userName + "下线了！");
        message2Client.setNames(names);
        // 发送信息
        String jsonStr = CommUtils.object2Json(message2Client);
        for (WebSocket webSocket :
                clients) {
            webSocket.sendMsg(jsonStr);
        }
    }

    /**
     * 每个WebSocket对象设置session信息
     * 通过Json字符串进行前后端交互
     * 后端向前端发送信息
     * @param msg Json序列化要发送的信息对象的字符串
     */
    public void sendMsg(String msg) {
        try {
            /*
            * 返回客户端，session：会话的对象
            * getBasicRemote()：获取当前的remote
            * 现在在服务器端，remote为浏览器端（session的另一端）
            * sendText()：信息推送给浏览器
            * */
            this.session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
