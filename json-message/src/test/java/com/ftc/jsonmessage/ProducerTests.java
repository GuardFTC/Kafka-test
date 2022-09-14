package com.ftc.jsonmessage;

import cn.hutool.json.JSONObject;
import com.ftc.jsonmessage.config.KafkaSendCallBackForJson;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class ProducerTests {

    @Autowired
    private KafkaTemplate<String, JSONObject> kafkaTemplate;

    @Autowired
    private KafkaSendCallBackForJson kafkaSendCallBackForJson;

    @AfterEach
    @SneakyThrows({InterruptedException.class})
    void stopSeconds() {
        TimeUnit.MILLISECONDS.sleep(500);
    }

    @Test
    void sendJson() {

        //1.创建消息
        JSONObject message = new JSONObject(true);
        message.set("id", 1);
        message.set("name", "ftc");

        //2.发送消息
        kafkaTemplate.send("json-message-topic", message)
                .addCallback(kafkaSendCallBackForJson);
    }
}
