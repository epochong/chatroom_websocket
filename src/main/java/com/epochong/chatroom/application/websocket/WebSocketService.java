package com.epochong.chatroom.application.websocket;

import com.epochong.chatroom.controller.assember.MessageAssembler;
import com.epochong.chatroom.domian.value.Message2Client;
import com.epochong.chatroom.domian.value.MessageFromClient;
import com.epochong.chatroom.infrastructure.repository.utils.CommUtils;
import com.epochong.chatroom.infrastructure.repository.utils.Constant;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
@ServerEndpoint("/websocket")
public class WebSocketService {
    /**
     * 存储所有连接到后端的websocket每个对象都有这个集合，Tomcat每次打开一个页面就重新建立一个对象
     */
    public static CopyOnWriteArraySet<WebSocketService> clients = new CopyOnWriteArraySet <>();

    /**
     * 缓存所有的用户映射信息
     * session id --> 用户名
     */
    public static Map<String, String> names = new ConcurrentHashMap <>();
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
     * 用户类型
     */
    private Integer userType;

    /**
     * 当前端websocket建立连接就会调用这个方法
     * 添加session
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        //绑定当前session
        this.session = session;
        // 如：username=${username}&userType=${userType}
        Map <String, String> urlParams = CommUtils.getUrlParams(session.getQueryString());
        this.userName = urlParams.get(Constant.USERNAME);
        this.userType = Integer.parseInt(urlParams.get(Constant.USER_TYPE));
        //将客户端聊天实体保存到clients
        clients.add(this);
        //将当前用户以及SessionID保存到用户列表
        names.put(session.getId(), userName);
        System.out.println("有新的连接，SessionID为" + session.getId() + ",用户名：" + userName + ",用户类型：" + urlParams.get(Constant.USER_TYPE));
        //发送给所有在线用户一个上线通知
        Message2Client message2Client = new Message2Client();
        if (Constant.FROM_USER_TYPE.equals(urlParams.get(Constant.USER_TYPE))) {
            message2Client.setContent(userName + "上线了,请问有什么能帮到您？");
        } else {
            message2Client.setContent("您好！我有以下问题想要咨询。");
        }
        message2Client.setUserType(urlParams.get(Constant.USER_TYPE));
        message2Client.setNames(names);
        // 用户之间不可看见消息，客服之间，客服和用户之间可以看到消息
        clients.stream()
                .filter(c -> (this.userType == 2 && c.userType == 1) || this.userType == 1 || c == this)
                .forEach(webSocketService -> webSocketService.sendMsg(CommUtils.object2Json(message2Client)));
    }

    @OnError
    public void onError(Throwable e) {
        log.error("WebSocket连接失败");
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
        MessageFromClient messageFromClient = (MessageFromClient) CommUtils.json2Object(msg, MessageFromClient.class);
        //1代表群聊
        if (messageFromClient.getType().equals(Constant.GROUP_CHAT_TYPE)) {
            //群聊的信息
            Message2Client message2Client = MessageAssembler.getMessage2Client(messageFromClient);
            //用户只能发送给客服，客服可以发送给客服和用户。广播发送
            clients.stream()
                    .filter(c -> (this.userType == 2 && c.userType == 1) || this.userType == 1)
                    .forEach(webSocketService -> webSocketService.sendMsg(CommUtils.object2Json(message2Client)));
        }
        //2：私聊信息
        else if (messageFromClient.getType().equals(Constant.PRIVATE_CHAT_TYPE)) {
            // {"to":"0-1-2-","msg":"33333","type":2}
            //to:私聊对象的SessionID
            int toL = messageFromClient.getTo().length();
            String[] toClientsArray = messageFromClient.getTo().substring(0,toL - 1).split("-");
            List<String> toClients = Arrays.asList(toClientsArray);
            //发送私聊信息
            Message2Client message2Client = MessageAssembler.getMessage2Client(messageFromClient, userName);
            //给指定的SessionID发送信息 ，SessionID从0开始，新建一个窗口加1
            clients.stream()
                    //在所有客户存在的客户中找到了勾选的私聊对象（通过ID）并且不是自己
                    .filter(webSocketService ->
                            toClients.contains(webSocketService.session.getId()) && !this.session.getId().equals(webSocketService.session.getId()))
                    .forEach(webSocketService -> webSocketService.sendMsg(CommUtils.object2Json(message2Client)));
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
        for (WebSocketService webSocketService : clients) {
            webSocketService.sendMsg(jsonStr);
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
