package com.sun.hospital.tools;

import gnu.io.*;

import java.io.*;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import static com.sun.activation.registries.LogSupport.log;

/**
 * @author 孙耘田
 * @date 2020/3/29 - 13:01
 */
public class SerialPortListener implements Runnable{


        private String appName = "串口通讯";

        private int timeout = 2000;//open 端口时的等待时间

        private int threadTime = 0;

        private String msg;

        private CommPortIdentifier commPort;

        private SerialPort serialPort;

        private OutputStream outputStream;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**

         * @方法名称 :listPort

         * @功能描述 :列出所有可用的串口

         * @返回值类型 :void

         */

        @SuppressWarnings("rawtypes")

        public void listPort(){

            CommPortIdentifier cpid;//当前串口对象

            Enumeration en = CommPortIdentifier.getPortIdentifiers();

            System.out.print("列出所有端口：");

            while(en.hasMoreElements()){

                cpid = (CommPortIdentifier)en.nextElement();

                //检测端口类型是否为串口

                if(cpid.getPortType() == CommPortIdentifier.PORT_SERIAL){

                    System.out.println(cpid.getName() + ", " + cpid.getCurrentOwner());

                }

            }

        }


        /**

         * @方法名称 :openPort

         * @功能描述 :选择一个端口，比如：COM1 并实例 SerialPort

         * @返回值类型 :void

         * @param portName

         */

        public void openPort(String portName){

            /* 打开该指定串口 */

            this.commPort = null;

            CommPortIdentifier cpid;

            Enumeration en = CommPortIdentifier.getPortIdentifiers();



            while(en.hasMoreElements()){

                cpid = (CommPortIdentifier)en.nextElement();

                if(cpid.getPortType() == CommPortIdentifier.PORT_SERIAL && cpid.getName().equals(portName)){

                    this.commPort = cpid;

                    break;

                }

            }

            /* 实例 SerialPort*/

            if(commPort == null)

                log(String.format("无法找到名字为'%1$s'的串口！", portName));

            else{

                log("当前端口："+commPort.getName());

                try{

                    //应用程序名【随意命名】，等待的毫秒数

                    serialPort = (SerialPort)commPort.open(appName, timeout);

                }catch(PortInUseException e){

                    // 端口已经被占用

                    throw new RuntimeException(String.format("端口'%1$s'正在使用中！",commPort.getName()));

                }

            }

        }



        /**

         * @方法名称 :checkPort

         * @功能描述 :检查端口是否正确连接

         * @返回值类型 :void

         */

        private void checkPort(){

            if(commPort == null)

                throw new RuntimeException("没有选择端口，请使用 " +"selectPort(String portName) 方法选择端口");



            if(serialPort == null){

                throw new RuntimeException("SerialPort 对象无效！");

            }

        }



        /**

         * @方法名称 :write

         * @功能描述 :向端口发送数据，请在调用此方法前 先选择端口，并确定SerialPort正常打开！

         * @返回值类型 :void

         *    @param message

         * @throws IOException

         */



        public void write(String message) throws InterruptedException {

            checkPort();

            try{

                outputStream = new BufferedOutputStream(serialPort.getOutputStream());

                outputStream.write(message.getBytes());

                log("消息:'"+message+"'发送成功!");

                outputStream.close();

            }catch(IOException e){

                throw new RuntimeException("向端口发送信息时出错："+e.getMessage());

            }

        }

    public byte[] startRead(int time) {

        checkPort();
        InputStream inputStream = null;
        byte[] bytes = null;
        try {

            inputStream = new BufferedInputStream(serialPort.getInputStream());

            int bufflenth = inputStream.available();        //获取buffer里的数据长度

            while (bufflenth != 0) {
                bytes = new byte[bufflenth];    //初始化byte数组为buffer中数据的长度
                inputStream.read(bytes);
                bufflenth = inputStream.available();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (time > 0) {

            this.threadTime = time * 1000;

            Thread t = new Thread(this);

            t.start();

            log(String.format("监听程序将在%1$d秒后关闭。。。。", time));

        }
        return bytes;
    }

        public void close(){

            serialPort.close();

            serialPort = null;

            commPort = null;

        }


        public void log(String msg){

            System.out.println(appName+" --> "+msg);

        }


    public void addListener(SerialPortEventListener listener)  {

        try {
            //给串口添加监听器
            serialPort.addEventListener(listener);
            //设置当有数据到达时唤醒监听接收线程
            serialPort.notifyOnDataAvailable(true);
            serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
            //设置当通信中断时唤醒中断线程
            serialPort.notifyOnBreakInterrupt(true);
        } catch (UnsupportedCommOperationException e) {
            e.printStackTrace();
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        }
    }


    public void removeListener(SerialPortEventListener listener){
            serialPort.removeEventListener();
            serialPort.close();
        log(String.format("端口'%1$s'关闭了！", commPort.getName()));
    }

        @Override

        public void run() {

            try{

                Thread.sleep(threadTime);

                serialPort.close();

                log(String.format("端口'%1$s'监听关闭了！", commPort.getName()));

            }catch(Exception e){

                e.printStackTrace();

            }

        }








}
