package com.ftc.jsonmessage.config.serialize;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-13 11:18:14
 * @describe: JSON反序列化器
 */
public class JsonDeserialize implements Deserializer<JSONObject> {

    @Override
    public JSONObject deserialize(String topic, byte[] data) {
        return JSONUtil.parseObj(StrUtil.str(data, StandardCharsets.UTF_8));
    }
}