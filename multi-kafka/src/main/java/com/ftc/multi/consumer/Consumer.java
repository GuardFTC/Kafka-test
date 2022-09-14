package com.ftc.multi.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-08 16:06:48
 * @describe: 多数据源监听
 */
@Component
public class Consumer {

    @KafkaListener(
            topics = {"multi-kafka-topic"},
            containerFactory = "primaryConsumerFactory",
            topicPartitions = {@TopicPartition(topic = "multi-kafka-topic", partitions = {"0"})}
    )
    public void primaryListener(ConsumerRecord<String, String> record) {
        System.out.println("主数据源_监听获取数据:" + record.value());
    }

    @KafkaListener(
            topics = {"multi-kafka-topic"},
            containerFactory = "secondaryConsumerFactory",
            topicPartitions = {@TopicPartition(topic = "multi-kafka-topic", partitions = {"1"})}
    )
    public void secondaryListener(ConsumerRecord<String, String> record) {
        System.out.println("备数据源_监听获取数据:" + record.value());
    }
}
