package com.ftc.multithread.consumer;

import com.ftc.multithread.service.MultiThreadListenerService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-08 16:06:48
 * @describe: 多线程消费者数据源监听
 */
@Component
@RequiredArgsConstructor
public class Consumer {

    private final MultiThreadListenerService multiThreadListenerService;

    @KafkaListener(topics = {"multi-thread-topic"}, groupId = "multi-thread-group")
    public void listener(ConsumerRecord<String, String> record) {
        multiThreadListenerService.doSomeThing(record);
    }
}