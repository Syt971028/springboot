package com.sun.hospital.server;

import com.alibaba.fastjson.JSON;
import com.sun.hospital.entiy.Message;
import com.sun.hospital.tools.Jsontools;
import com.sun.hospital.tools.SerialPortListener;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint(value = "/Rxtx/{username}")
@Component
public class RXTXServer {
    SerialPortListener spl = new SerialPortListener();

    /**
     * 全部在线会话  PS: 基于场景考虑 这里使用线程安全的Map存储会话对象。
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();
    private static Map<String,String> match = new ConcurrentHashMap<>();


    /**
     * 当客户端打开连接：1.添加会话对象 2.更新在线人数
     */
    @OnOpen
    public void onOpen(Session session,@PathParam(value = "username") String username) throws InterruptedException {
        match.put(username,session.getId());
        onlineSessions.put(session.getId(), session);

        spl.openPort("COM3");
        spl.addListener(new SerialPortEventListener() {
        @Override
            public void serialEvent(SerialPortEvent arg0) {
                byte[] bytes = spl.startRead(0);
                String readStr = null;
                switch (arg0.getEventType()) {
                    case SerialPortEvent.BI:/*Break interrupt,通讯中断*/
                    case SerialPortEvent.OE:/*Overrun error，溢位错误*/
                    case SerialPortEvent.FE:/*Framing error，传帧错误*/
                    case SerialPortEvent.PE:/*Parity error，校验错误*/
                    case SerialPortEvent.CD:/*Carrier detect，载波检测*/
                    case SerialPortEvent.CTS:/*Clear to send，清除发送*/
                    case SerialPortEvent.DSR:/*Data set ready，数据设备就绪*/
                    case SerialPortEvent.RI:/*Ring indicator，响铃指示*/
                    case SerialPortEvent.OUTPUT_BUFFER_EMPTY:/*Output buffer is empty，输出缓冲区清空*/
                        break;
                    case SerialPortEvent.DATA_AVAILABLE:/*Data available at the serial port，端口有可用数据。读到缓冲数组，输出到终端*/
                        readStr = new String(bytes);

                            sendMessageTo(Jsontools.getJson(readStr),username);


                }
            }
        });



    }

    /**
     * 当客户端发送消息：1.获取它的用户名和消息 2.发送消息给所有人
     * <p>
     * PS: 这里约定传递的消息为JSON字符串 方便传递更多参数！
     */
    @OnMessage
    public void onMessage( String jsonStr,@PathParam(value = "username") String username) {

        Message message = JSON.parseObject(jsonStr, Message.class);

        sendMessageTo(Message.jsonStr(Message.SPEAK, message.getUsername(), message.getMsg(), onlineSessions.size()),username);



    }

    /**
     * 当关闭连接：1.移除会话对象 2.更新在线人数
     */
    @OnClose
    public void onClose(Session session,@PathParam(value = "username") String username) {
        spl.removeListener(new SerialPortEventListener() {
            @Override
            public void serialEvent(SerialPortEvent ev) {

            }
        });
        match.remove(username);
        onlineSessions.remove(session.getId());
        sendMessageTo(Message.jsonStr(Message.QUIT, username, "", onlineSessions.size()),username);
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
    private static void sendMessageTo(String msg,@PathParam(value = "username") String username) {
        match.forEach((name,id) ->{

            if(name.equals(username)){
                onlineSessions.get(id).getAsyncRemote()
                        .sendText(msg);
            }

        });
    }




}
