package com.ftc.ordermessage.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-13 10:01:26
 * @describe: 顺序消息消费者V1
 */
@Component
public class OrderMessageConsumerV1 {

    @KafkaListener(topics = {"order-message-topic-v1"}, groupId = "${spring.kafka.consumer.group-id}")
    public void listenerN1(ConsumerRecord<String, String> record) {
        System.out.println("receive message N1:" + record.value());
    }

    @KafkaListener(topics = {"order-message-topic-v1"}, groupId = "${spring.kafka.consumer.group-id}")
    public void listenerN2(ConsumerRecord<String, String> record) {
        System.out.println("receive message N2:" + record.value());
    }
}
