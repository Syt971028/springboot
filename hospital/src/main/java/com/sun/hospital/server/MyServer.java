package com.sun.hospital.server;

import com.alibaba.fastjson.JSON;
import com.sun.hospital.entiy.Message;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



@ServerEndpoint(value = "/talk/{username}/{pname}")
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
    public void onOpen(Session session,@PathParam(value = "username") String username,@PathParam(value = "pname") String pname) {
        System.out.println(session.getId()+username);
        match.put(username,session.getId());
        onlineSessions.put(session.getId(), session);
        sendMessageTo(Message.jsonStr(Message.ENTER, "", "", onlineSessions.size()),pname,username);
    }

    /**
     * 当客户端发送消息：1.获取它的用户名和消息 2.发送消息给所有人
     * <p>
     * PS: 这里约定传递的消息为JSON字符串 方便传递更多参数！
     */
    @OnMessage
    public void onMessage(String jsonStr,@PathParam(value = "pname") String pname,
                          @PathParam(value = "username") String username) {
        System.out.println(pname);
        Message message = JSON.parseObject(jsonStr, Message.class);
        sendMessageTo(Message.jsonStr(Message.SPEAK, message.getUsername(), message.getMsg(), onlineSessions.size()),pname,username);

    }

    /**
     * 当关闭连接：1.移除会话对象 2.更新在线人数
     */
    @OnClose
    public void onClose(Session session,@PathParam(value = "pname") String pname,@PathParam(value = "username") String username) {
        match.remove(username);
        onlineSessions.remove(session.getId());
        sendMessageTo(Message.jsonStr(Message.QUIT, "", "", onlineSessions.size()),pname,username);
    }

    /**
     * 当通信发生异常：打印错误日志
     */
    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }


    private static void sendMessageTo(String msg,@PathParam(value = "pname") String pname,
                                         @PathParam(value = "username") String username) {
        match.forEach((name,id) ->{
            if(name.equals(pname)){
                onlineSessions.get(id).getAsyncRemote()
                        .sendText(msg);
            }
            if(name.equals(username)){
                onlineSessions.get(id).getAsyncRemote()
                        .sendText(msg);
            }

        });
    }




}
