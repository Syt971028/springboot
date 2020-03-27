package com.sun.hospital.server;

import com.alibaba.fastjson.JSON;
import com.sun.hospital.entiy.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



@ServerEndpoint(value = "/talk/{username}")
@Component
public class MyServer {

    /**
     * 全部在线会话  PS: 基于场景考虑 这里使用线程安全的Map存储会话对象。
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();
    private static Map<String,String> match = new ConcurrentHashMap<>();


    /**
     * 当客户端打开连接：1.添加会话对象 2.更新在线人数
     */
    @OnOpen
    public void onOpen(Session session,@PathParam(value = "username") String username) {
        match.put(session.getId(),username);
        onlineSessions.put(session.getId(), session);
        sendMessageToAll(Message.jsonStr(Message.ENTER, "", "", onlineSessions.size()));
        System.out.println(username+"会话开启成功");
    }

    /**
     * 当客户端发送消息：1.获取它的用户名和消息 2.发送消息给所有人
     * <p>
     * PS: 这里约定传递的消息为JSON字符串 方便传递更多参数！
     */
    @OnMessage
    public void onMessage(String jsonStr) {
        Message message = JSON.parseObject(jsonStr, Message.class);
        match.forEach((id,name) ->{
            if(name.equals(message.getUsername())){
                onlineSessions.get(id).getAsyncRemote().sendText(Message.jsonStr(Message.SPEAK, message.getUsername(), message.getMsg(), onlineSessions.size()));
            }
        });
        System.out.println("发送消息");
    }

    /**
     * 当关闭连接：1.移除会话对象 2.更新在线人数
     */
    @OnClose
    public void onClose(Session session,@PathParam(value = "username") String username) {
        match.remove(username);
        onlineSessions.remove(session.getId());
        sendMessageToAll(Message.jsonStr(Message.QUIT, "", "", onlineSessions.size()));
        System.out.println(username+"会话关闭");
    }

    /**
     * 当通信发生异常：打印错误日志
     */
    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

    /**
     * 公共方法：发送信息给所有人
     */
    private static void sendMessageToAll(String msg) {
        onlineSessions.forEach((id, session) -> {
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }




}