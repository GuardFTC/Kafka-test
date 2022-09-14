package com.ftc.jsonmessage.config.serialize;

import cn.hutool.json.JSONObject;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-13 11:27:20
 * @describe: JSON序列化器
 */
public class JsonSerialize implements Serializer<JSONObject> {

    @Override
    public byte[] serialize(String topic, JSONObject data) {
        return data.toString().getBytes(StandardCharsets.UTF_8);
    }
}