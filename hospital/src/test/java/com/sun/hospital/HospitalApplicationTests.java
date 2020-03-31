package com.sun.hospital;

import com.sun.hospital.tools.SerialPortListener;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HospitalApplicationTests {

    @Test
    void contextLoads() throws InterruptedException {
        SerialPortListener sp = new SerialPortListener();
        sp.openPort("COM3");
        int a=20;
        sp.startRead(0);

        while (true){
            Thread.sleep(a);
            System.out.println(sp.getMsg());
        }
    }




}
