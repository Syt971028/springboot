package com.sun.hospital.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

/**
 * @author 孙耘田
 * @date 2020/3/24 - 22:52
 */
public class Jsontools {
    public static String getJson(Object object) {
        return getJson(object, "yyyy-MM-dd HH-mm-ss");
    }

    public static String getJson(Object object, String dateFormat) {
        ObjectMapper mapper = new ObjectMapper();
        //关闭时间戳
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //时间格格式自定义
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        //给mapper指定simpleDateFormat格式
        mapper.setDateFormat(simpleDateFormat);
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
