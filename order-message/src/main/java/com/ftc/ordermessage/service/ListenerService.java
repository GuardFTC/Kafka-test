package com.ftc.ordermessage.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-13 14:11:38
 * @describe: 多线程消费者逻辑处理类
 */
@Component
public class ListenerService {

    @Async("kafkaListenerExecutor-1")
    public void doSomeThingNumber1(ConsumerRecord<String, String> record) {
        doSomeThing(record);
    }

    @Async("kafkaListenerExecutor-2")
    public void doSomeThingNumber2(ConsumerRecord<String, String> record) {
        doSomeThing(record);
    }

    @Async("kafkaListenerExecutor-3")
    public void doSomeThingNumber3(ConsumerRecord<String, String> record) {
        doSomeThing(record);
    }

    /**
     * 处理业务逻辑
     *
     * @param record 消息
     */
    private void doSomeThing(ConsumerRecord<String, String> record) {
        System.out.println(Thread.currentThread().getName() + "线程消费者_监听获取数据:" + record.value());
    }
}