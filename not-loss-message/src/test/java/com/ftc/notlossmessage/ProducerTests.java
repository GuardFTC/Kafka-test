package com.ftc.notlossmessage;

import com.ftc.notlossmessage.config.KafkaSendCallBackForString;
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
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaSendCallBackForString kafkaSendCallBackForString;

    @AfterEach
    @SneakyThrows(InterruptedException.class)
    void sleep() {
        TimeUnit.SECONDS.sleep(30);
    }

    @Test
    void sendMessage() {

        //1.发送消息
        kafkaTemplate.send("not-loss-message-topic", "not-loss-message")
                .addCallback(kafkaSendCallBackForString);
    }
}
