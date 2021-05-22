package com.epochong.chatroom.application.websocket;

import com.epochong.chatroom.application.command.service.MessageCommandService;
import com.epochong.chatroom.application.command.service.RobotCommandService;
import com.epochong.chatroom.application.command.service.impl.MessageCommandServiceImpl;
import com.epochong.chatroom.application.command.service.impl.RobotCommandServiceImpl;
import com.epochong.chatroom.application.query.service.MessageQueryService;
import com.epochong.chatroom.application.query.service.RobotQueryService;
import com.epochong.chatroom.application.query.service.impl.MessageQueryServiceImpl;
import com.epochong.chatroom.application.query.service.impl.RobotQueryServiceImpl;
import com.epochong.chatroom.controller.assember.MessageAssembler;
import com.epochong.chatroom.controller.assember.RobotAssembler;
import com.epochong.chatroom.controller.assember.UserAssembler;
import com.epochong.chatroom.controller.dto.MessageDto;
import com.epochong.chatroom.controller.dto.RobotDto;
import com.epochong.chatroom.domian.entity.Message;
import com.epochong.chatroom.domian.entity.Robot;
import com.epochong.chatroom.domian.entity.User;
import com.epochong.chatroom.domian.value.BaseResp;
import com.epochong.chatroom.domian.value.Message2Client;
import com.epochong.chatroom.domian.value.MessageFromClient;
import com.epochong.chatroom.infrastructure.repository.utils.CommUtils;
import com.epochong.chatroom.infrastructure.repository.utils.Constant;
import com.epochong.chatroom.infrastructure.repository.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
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
     * 缓存消息,0:缓存用户问题，1:缓存客服回答
     */
    public static ArrayList<String> faqAnswer = new ArrayList <>(2);
    /**
     * 绑定当前websocket会话
     * 每个浏览器窗口建立起来就是一个session会话
     */
    private Session session;

    /**
     * 当前客户端信息
     */
    private User user;

    private RobotCommandService robotCommandService = new RobotCommandServiceImpl();
    private RobotQueryService robotQueryService = new RobotQueryServiceImpl();
    private MessageCommandService messageCommandService = new MessageCommandServiceImpl();
    private MessageQueryService messageQueryService = new MessageQueryServiceImpl();
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
        this.user = UserAssembler.getUser(urlParams);
        //将客户端聊天实体保存到clients
        clients.add(this);
        //将当前用户以及SessionID保存到用户列表
        names.put(session.getId(), user.getUserName());
        log.info("有新的连接，SessionID为" + session.getId() + ",用户名：" + this.user.getUserName() + ",用户类型：" + urlParams.get(Constant.USER_TYPE));
        //发送给所有在线用户一个上线通知
        Message2Client message2Client = new Message2Client();
        if (Constant.INT_FROM_USER_TYPE == user.getUserType()) {
            message2Client.setContent(user.getUserName() + "上线了，" + "请问有什么能帮到您？");
        } else {
            message2Client.setContent("您好！我有以下问题想要咨询。");
        }
        message2Client.setType(user.getUserType());
        message2Client.setTitleName(user.getUserName());
        message2Client.setNames(names);
        // 历史消息展示
        showHistoryMessage();
        // 用户之间不可看见消息，客服之间，客服和用户之间可以看到消息
        clients.stream()
                .filter(c -> (user.getUserType() == Constant.INT_TO_USER_TYPE
                        && c.user.getUserType() == Constant.INT_FROM_USER_TYPE)
                        || user.getUserType() == Constant.INT_FROM_USER_TYPE
                        || c == this)
                .forEach(client -> {
                            // 发送前端消息
                            client.sendMsg(CommUtils.object2Json(message2Client));
                        });
        // 数据库存入消息
        clients.forEach(c -> {
                    // 如果是用户，则发给所有客服即可
                    if (user.getUserType() == Constant.INT_TO_USER_TYPE && c.user.getUserType() == Constant.INT_FROM_USER_TYPE) {
                        MessageDto messageDto = new MessageDto();
                        messageDto.setFromUserId(c.user.getId());
                        messageDto.setFromUserName(c.user.getUserName());
                        messageDto.setToUserId(user.getId());
                        messageDto.setToUserName(user.getUserName());
                        messageDto.setContent(message2Client.getContent());
                        messageDto.setType(user.getUserType());
                        saveMessage(messageDto);
                    }
                    // 如果是客服发送的消息，要发给所有用户及
                    if (user.getUserType() == Constant.INT_FROM_USER_TYPE && c.user.getUserType() == Constant.INT_TO_USER_TYPE) {
                        MessageDto messageDto = new MessageDto();
                        messageDto.setFromUserId(user.getId());
                        messageDto.setFromUserName(user.getUserName());
                        messageDto.setToUserId(c.user.getId());
                        messageDto.setToUserName(c.user.getUserName());
                        messageDto.setContent(message2Client.getContent());
                        messageDto.setType(user.getUserType());
                        saveMessage(messageDto);
                    }
                });
    }

    private void showHistoryMessage() {
        MessageDto queryMessage = new MessageDto();
        queryMessage.setToUserId(user.getId());
        queryMessage.setFromUserId(user.getId());
        queryMessage.setUserType(user.getUserType());
        log.info("showHistoryMessage(): request:{}" , queryMessage);
        BaseResp baseResp = messageQueryService.queryMessageByUserId(queryMessage);
        log.info("showHistoryMessage(): response:{}", baseResp);
        if (Objects.nonNull(baseResp.getObject())) {
            List<Message> messageList = (List <Message>) baseResp.getObject();
            messageList.forEach(m -> {
                Message2Client historyMessage = new Message2Client();
                historyMessage.setContent(m.getContent());
                historyMessage.setMessageTime(TimeUtils.timestampToStr(m.getCreateTime()));
                historyMessage.setType(m.getType());
                if (m.getType() == Constant.INT_FROM_USER_TYPE) {
                    historyMessage.setTitleName(m.getFromUserName());
                } else if (m.getType() == Constant.INT_ROBOT_USER_TYPE){
                    historyMessage.setTitleName(m.getFromUserName());
                } else {
                    historyMessage.setTitleName(m.getToUserName());
                }
                this.sendMsg(CommUtils.object2Json(historyMessage));
            });
        }
    }

    private void saveMessage(MessageDto messageDto) {
        log.info("saveMessage() request: {}", messageDto);
        BaseResp baseResp = messageCommandService.insertRobotFaq(messageDto);
        if (baseResp.getCode() == Constant.SUCCESS) {
            log.info("插入消息成功：{}", baseResp);
        } else {
            log.error("插入消息失败：{}", baseResp);
        }
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
                    .filter(c -> (user.getUserType() == 2 && c.user.getUserType() == 1) || user.getUserType() == 1)
                    .forEach(client -> client.sendMsg(CommUtils.object2Json(message2Client)));
        }
        //2：私聊信息
        else if (messageFromClient.getType().equals(Constant.PRIVATE_CHAT_TYPE)) {
            List <String> toClients = getToClientsList(messageFromClient);
            //发送私聊信息
            Message2Client message2Client = MessageAssembler.getMessage2Client(messageFromClient);
            //给指定的SessionID发送信息 ，SessionID从0开始，新建一个窗口加1
            clients.stream()
                    //在所有客户存在的客户中找到了勾选的私聊对象（通过ID）并且不是自己
                    .filter(client ->
                            toClients.contains(client.session.getId()) && this != client)
                    .forEach(client -> {
                        client.sendMsg(CommUtils.object2Json(message2Client));
                        // 如果client是用户则一定是客服发给用户的消息，因为只有客服能给用户发消息，用户不能给用户发消息
                        if (client.user.getUserType() == Constant.INT_TO_USER_TYPE) {
                            MessageDto messageDto = new MessageDto();
                            messageDto.setFromUserId(user.getId());
                            messageDto.setFromUserName(user.getUserName());
                            messageDto.setToUserId(client.user.getId());
                            messageDto.setToUserName(client.user.getUserName());
                            messageDto.setContent(message2Client.getContent());
                            messageDto.setType(user.getUserType());
                            saveMessage(messageDto);
                        }
                        // 如果是用户发给客服的，用户的列表里只有客服，之前也filter下来已经勾选的客服了
                        else if (user.getUserType() == Constant.INT_TO_USER_TYPE){
                            MessageDto messageDto = new MessageDto();
                            messageDto.setFromUserId(client.user.getId());
                            messageDto.setFromUserName(client.user.getUserName());
                            messageDto.setToUserId(user.getId());
                            messageDto.setToUserName(user.getUserName());
                            messageDto.setContent(message2Client.getContent());
                            messageDto.setType(user.getUserType());
                            saveMessage(messageDto);
                        }
                    });
        }
        // 如果机器人没有该问题,或者是客服发的消息，需要存储消息
        if (!isRobotHasFaq(messageFromClient) || Constant.FROM_USER_TYPE.equals(messageFromClient.getFromUserType())) {
            // 存储问题或回复
            saveFaq(messageFromClient);
        }
    }

    private Boolean isRobotHasFaq(MessageFromClient messageFromClient) {
        // 如果是用户提的问题先查询有没有机器人回复语
        BaseResp baseResp = new BaseResp();
        if (Constant.TO_USER_TYPE.equals(messageFromClient.getFromUserType())) {
            RobotDto dto = new RobotDto();
            dto.setFaq(messageFromClient.getMsg());
            log.info("WebSocketService.isRobotHasFaq(): request:{}", dto);
            baseResp = robotQueryService.queryAnswer(dto);
            log.info("WebSocketService.isRobotHasFaq(): response:{}", baseResp);
            MessageFromClient robotMessage = new MessageFromClient();
            robotMessage.setFromUserType(Constant.ROBOT_USER_TYPE);
            robotMessage.setMsg(baseResp.getMessage());
            robotMessage.setTo(session.getId());
            List <String> toClients = getToClientsList(messageFromClient);
            Message2Client message2Client = MessageAssembler.getMessage2Client(robotMessage);
            clients.stream()
                    //用户自己或者所有勾选的客服
                    .filter(client -> client == this || toClients.contains(client.session.getId()) )
                    .forEach(client -> {
                        if (client != this) {
                            message2Client.setTitleName(Constant.ROBOT_NAME + client.user.getUserName());
                        } else {
                            message2Client.setTitleName(Constant.ROBOT_NAME);
                        }
                        client.sendMsg(CommUtils.object2Json(message2Client));
                        if (client != this) {
                            MessageDto messageDto = new MessageDto();
                            messageDto.setFromUserId(client.user.getId());
                            messageDto.setFromUserName(Constant.ROBOT_NAME + client.user.getUserName());
                            messageDto.setToUserId(user.getId());
                            messageDto.setToUserName(user.getUserName());
                            messageDto.setContent(message2Client.getContent());
                            messageDto.setType(Constant.INT_ROBOT_USER_TYPE);
                            saveMessage(messageDto);
                        }
                    });
        }
        return baseResp.getCode() == Constant.SUCCESS;
    }

    private void saveFaq(MessageFromClient messageFromClient) {
        // 如果是用户提的问题先缓存起来
        if (Constant.TO_USER_TYPE.equals(messageFromClient.getFromUserType()) && faqAnswer.size() == 0) {
            faqAnswer.add(messageFromClient.getMsg());
        }
        // 如果是客服回答的，则放到对应的回答里
        if (Constant.FROM_USER_TYPE.equals(messageFromClient.getFromUserType()) && faqAnswer.size() == 1){
            faqAnswer.add(messageFromClient.getMsg());
            // 问题回复对应入库
            Robot robot = new Robot();
            robot.setFaq(faqAnswer.get(0));
            robot.setAnswer(faqAnswer.get(1));
            robot.setFaqValid(Constant.FAQ_VALID);
            RobotDto robotDto = RobotAssembler.getRobot(robot);
            log.info("saveFaq() request:{}", robotDto);
            BaseResp baseResp = robotCommandService.insertRobotFaq(robotDto);
            log.info("saveFaq() response:{}", baseResp);
            if (Objects.nonNull(baseResp)) {
                faqAnswer.clear();
            }
        }
        log.info("saveFaq() messageFromClient={}, faqAnswer={}", messageFromClient, faqAnswer);
    }

    private List<String> getToClientsList(MessageFromClient messageFromClient) {
        // {"to":"0-1-2-","msg":"33333","type":2}
        //to:私聊对象的SessionID
        if (Objects.isNull(messageFromClient.getTo())) {
            return Collections.emptyList();
        }
        int toL = messageFromClient.getTo().length();
        String[] toClientsArray = messageFromClient.getTo().substring(0,toL - 1).split("-");
        List<String> toClients = Arrays.asList(toClientsArray);
        return toClients;
    }

    @OnClose
    public void onClose() {


        log.info("有连接下线了,用户名为" + user.getUserName());
        Message2Client message2Client = new Message2Client();
        message2Client.setContent(user.getUserName() + "下线了！");
        message2Client.setNames(names);
        message2Client.setTitleName(user.getUserName());
        message2Client.setType(user.getUserType());
        // 发送信息
        clients.stream()
                .filter(c -> !(user.getUserType() == Constant.INT_TO_USER_TYPE && c.user.getUserType() == Constant.INT_TO_USER_TYPE))
                .forEach(c -> c.sendMsg(CommUtils.object2Json(message2Client)));
        // 将客户端聊天实体移除
        clients.remove(this);
        // 将当前用户以及SessionID移除用户列表
        names.remove(session.getId());
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
            log.info("向前端发送消息：{}", msg);
            this.session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
