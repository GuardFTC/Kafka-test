package com.ftc.multithread;

import com.ftc.multithread.config.KafkaSendCallBackForString;
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

        //1.循环发送消息
        for (int i = 1; i <= 100; i++) {
            kafkaTemplate.send("multi-thread-topic", i + "")
                    .addCallback(kafkaSendCallBackForString);
        }
    }
}
