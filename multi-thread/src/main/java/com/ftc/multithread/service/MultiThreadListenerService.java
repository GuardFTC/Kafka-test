package com.ftc.multithread.service;

import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-13 14:11:38
 * @describe: 多线程消费者逻辑处理类
 */
@Component
@Async("kafkaListenerExecutor")
public class MultiThreadListenerService {

    /**
     * 处理业务逻辑
     *
     * @param record 消息
     */
    @SneakyThrows(InterruptedException.class)
    public void doSomeThing(ConsumerRecord<String, String> record) {

        //1.模拟业务流程耗时
        TimeUnit.MILLISECONDS.sleep(1000);

        //2.控制台打印
        System.out.println(Thread.currentThread().getName() + "线程消费者_监听获取数据:" + record.value());
    }
}