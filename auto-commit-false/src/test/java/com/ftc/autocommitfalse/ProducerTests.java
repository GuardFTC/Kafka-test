package com.ftc.autocommitfalse;

import com.ftc.autocommitfalse.config.KafkaSendCallBackForString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
class ProducerTests {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaSendCallBackForString kafkaSendCallBackForString;

    @Test
    void sendMessage() {

        //1.发送消息
        kafkaTemplate.send("auto-commit-false-topic", "auto-commit-false-message")
                .addCallback(kafkaSendCallBackForString);
    }
}
