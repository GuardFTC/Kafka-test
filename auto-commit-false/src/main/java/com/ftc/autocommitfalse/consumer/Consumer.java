package com.ftc.autocommitfalse.consumer;

import cn.hutool.core.util.ObjectUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-13 10:01:26
 * @describe: 手动提交偏移量消费者
 */
@Component
public class Consumer {

    @KafkaListener(topics = {"auto-commit-false-topic"}, groupId = "auto-commit-false-topic-v1")
    public void listenerBySyncCommit(ConsumerRecord<String, String> record, org.apache.kafka.clients.consumer.Consumer<String, String> consumer) {

        //1.处理消息
        System.out.println("sync receive message:" + record.value());

        //2.同步 提交偏移量
        consumer.commitSync(Duration.of(2000, ChronoUnit.MILLIS));
    }

    @KafkaListener(topics = {"auto-commit-false-topic"}, groupId = "auto-commit-false-topic-v2")
    public void listenerByAsyncCommit(ConsumerRecord<String, String> record, org.apache.kafka.clients.consumer.Consumer<String, String> consumer) {

        //1.处理消息
        System.out.println("async receive message:" + record.value());

        //2.异步提交偏移量
        consumer.commitAsync((offsets, exception) -> {
            if (ObjectUtil.isNotNull(exception)) {

                //3.打印异常信息
                exception.printStackTrace();

                //4.TODO 进行消息重发等机制
            }
        });
    }
}
