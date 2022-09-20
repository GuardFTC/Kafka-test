package com.ftc.multi;

import com.ftc.multi.config.KafkaSendCallBackForString;
import com.ftc.multi.producer.TransactionProducer;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class ProducerTests {

    @Resource
    @Qualifier("primaryProducerTemplate")
    private KafkaTemplate<String, String> primaryTemplate;

    @Resource
    @Qualifier("secondaryProducerTemplate")
    private KafkaTemplate<String, String> secondaryTemplate;

    @Autowired
    private KafkaSendCallBackForString kafkaSendCallBackForString;

    @Autowired
    private TransactionProducer transactionProducer;

    @AfterEach
    @SneakyThrows({InterruptedException.class})
    void stopSeconds() {
        TimeUnit.MILLISECONDS.sleep(500);
    }

    @Test
    void send() {

        //1.主数据源发送消息
        primaryTemplate.send("multi-kafka-topic",
                0,
                "primary",
                "primary_message"
        ).addCallback(kafkaSendCallBackForString);

        //2.备数据源发送消息
        secondaryTemplate.send(
                "multi-kafka-topic",
                1,
                "secondary",
                "secondary_message"
        ).addCallback(kafkaSendCallBackForString);
    }

    @Test
    void sendWithErrorPrimary() {
        transactionProducer.sendMessageWithErrorPrimary();
    }

    @Test
    void sendWithErrorSecondary() {
        transactionProducer.sendMessageWithErrorSecondary();
    }
}
