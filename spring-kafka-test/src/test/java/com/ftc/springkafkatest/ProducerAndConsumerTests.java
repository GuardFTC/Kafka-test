package com.ftc.springkafkatest;

import cn.hutool.log.StaticLog;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@EmbeddedKafka(
        topics = {"embedded-topic"},
        brokerProperties = {
                "log.dirs=D:\\Code\\Projects\\Java\\kafka-test\\spring-kafka-test\\logs",
                "log.flush.interval.messages=1000",
                "log.flush.interval.ms=1000",
        }
)
class ProducerAndConsumerTests {

    @Resource
    @Qualifier("embeddedProducer")
    KafkaTemplate<String, String> embeddedProducer;

    @AfterEach
    @SneakyThrows(InterruptedException.class)
    void sleep() {
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    void kafka() {

        //1.发送消息
        embeddedProducer
                .send("embedded-topic", "test-embedded-message")
                .addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

                    @Override
                    public void onSuccess(SendResult<String, String> result) {
                        String message = result.getProducerRecord().value();
                        StaticLog.info("send kafka success message:" + message);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    @KafkaListener(topics = "embedded-topic", containerFactory = "embeddedConsumerFactory")
    public void listen(ConsumerRecord<String, String> record) {
        StaticLog.info("receive kafka message:" + record.value());
    }
}
