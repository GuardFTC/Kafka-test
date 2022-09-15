package com.ftc.ordermessage;

import com.ftc.ordermessage.config.KafkaSendCallBackForString;
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
    void sendOrderMessageV1() {

        //1.循环发送消息
        for (int i = 0; i < 100; i++) {

            //2.发送消息
            kafkaTemplate.send("order-message-topic-v1", i + "")
                    .addCallback(kafkaSendCallBackForString);
        }
    }

    @Test
    void sendOrderMessageV2() {

        //1.发送数字类型消息
        for (int i = 0; i < 100; i++) {

            //2.发送消息
            kafkaTemplate.send("order-message-topic-v2", "Integer", i + "")
                    .addCallback(kafkaSendCallBackForString);
        }

        //3.发送字母类型消息
        for (int i = 0; i < 26; i++) {

            //4.发送消息
            kafkaTemplate.send("order-message-topic-v2", "CHARACTER", (char) (97 + i) + "")
                    .addCallback(kafkaSendCallBackForString);
        }
    }
}
