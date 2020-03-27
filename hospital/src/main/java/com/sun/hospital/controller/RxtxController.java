package com.sun.hospital.controller;

import gnu.io.*;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 孙耘田
 * @date 2020/3/26 - 22:06
 */
@Controller
public class RxtxController {


    public void comm() {


/**
 * com3PollingListener类使用“轮训”的方法监听串口com3，
 * 并通过com3的输入流对象来获取该端口接收到的数据（在本文中数据来自串口COM11）。
 */

        CommPortIdentifier com3 = null;//未打卡的端口
        SerialPort serialcom3 = null;//打开的端口

        try {
            //2.获取并打开串口com3
            com3 = CommPortIdentifier.getPortIdentifier("COM3");
            serialcom3 = (SerialPort) com3.open("com3Listener", 1000);

            //3.获取串口的输入流对象
            InputStream inputStream = serialcom3.getInputStream();

            //4.从串口读入数据
            //定义用于缓存读入数据的数组
            byte[] cache = new byte[1024];
            //记录已经到达串口com3且未被读取的数据的字节（Byte）数。
            int availableBytes = 0;

            //无限循环，每隔20毫秒对串口com3进行一次扫描，检查是否有数据到达
            while (true) {
                //获取串口com3收到的可用字节数
                availableBytes = inputStream.available();
                //如果可用字节数大于零则开始循环并获取数据
                while (availableBytes > 0) {
                    //从串口的输入流对象中读入数据并将数据存放到缓存数组中
                    inputStream.read(cache);
                    //将获取到的数据进行转码并输出
                    for (int j = 0; j < cache.length && j < availableBytes; j++) {
                        //因为COM11口发送的是使用byte数组表示的字符串，
                        //所以在此将接收到的每个字节的数据都强制装换为char对象即可，
                        //这是一个简单的编码转换，读者可以根据需要进行更加复杂的编码转换。
                        System.out.print((char) cache[j]);
                    }
                    System.out.println();
                    //更新循环条件
                    availableBytes = inputStream.available();
                }
                //让线程睡眠20毫秒
                Thread.sleep(20);
            }

        } catch (IOException | NoSuchPortException | PortInUseException | InterruptedException e) {
            //如果获取输出流失败，则抛出该异常
            e.printStackTrace();
        }
    }


}
