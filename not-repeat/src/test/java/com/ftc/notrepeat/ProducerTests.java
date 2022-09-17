package com.ftc.notrepeat;

import com.ftc.notrepeat.config.KafkaSendCallBackForString;
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
        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    void sendMessage() throws InterruptedException {

        //1.发送第一批消息
        for (int i = 1; i <= 4; i++) {
            kafkaTemplate.send("not-repeat-message-topic", i + "")
                    .addCallback(kafkaSendCallBackForString);
        }

        //2.睡10s钟
        TimeUnit.SECONDS.sleep(10);

        //3.发送第二批消息
        kafkaTemplate.send("not-repeat-message-topic", "5")
                .addCallback(kafkaSendCallBackForString);
    }
}
