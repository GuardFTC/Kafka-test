package com.ftc.transactionkafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-13 10:01:26
 * @describe: 消费者demo
 */
@Component
public class Consumer {

    @KafkaListener(topics = {"transaction-topic"}, groupId = "${spring.kafka.consumer.group-id}")
    public void listenerDemo(ConsumerRecord<String, String> record) {
        System.out.println("receive message:" + record.value());
    }
}
