package com.ftc.jsonmessage.consumer;

import cn.hutool.json.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-08 16:06:48
 * @describe: JSON数据源监听
 */
@Component
public class JsonConsumer {

    @KafkaListener(topics = {"json-message-topic"}, groupId = "json-message-group")
    public void listener(ConsumerRecord<String, JSONObject> record) {
        System.out.println("JSON数据源_监听获取数据:" + record.value().toString());
    }
}