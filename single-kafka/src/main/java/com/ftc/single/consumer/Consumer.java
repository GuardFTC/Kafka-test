package com.ftc.single.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-13 10:01:26
 * @describe: 消费者demo
 */
@Component
public class Consumer {

    @KafkaListener(topics = {"single-kafka-topic"})
    public void listenerDemo(ConsumerRecord<String, String> record) {
        System.out.println("receive message:" + record.value());
    }

    @KafkaListener(topics = {"single-kafka-topic"}, groupId = "${spring.kafka.consumer.group-id}")
    public void listenerDemoV2(ConsumerRecord<String, String> record) {
        System.out.println("receive message:" + record.value());
    }

    @KafkaListener(topics = {"single-kafka-topic"}, groupId = "${spring.kafka.consumer.group-id}",
            topicPartitions = {@TopicPartition(topic = "single-kafka-topic", partitions = {"0"})}
    )
    public void listenerDemoV3(ConsumerRecord<String, String> record) {
        System.out.println("receive message from partition 0:" + record.value());
    }
}
