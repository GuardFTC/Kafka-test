package com.ftc.multi.producer;

import com.ftc.multi.config.KafkaSendCallBackForString;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-19 19:06:26
 * @describe: 事务生产者
 */
@Component
public class TransactionProducer {

    @Resource
    @Qualifier("primaryProducerTemplate")
    private KafkaTemplate<String, String> primaryTemplate;

    @Resource
    @Qualifier("secondaryProducerTemplate")
    private KafkaTemplate<String, String> secondaryTemplate;

    @Resource
    private KafkaSendCallBackForString kafkaSendCallBackForString;

    @Transactional(rollbackFor = RuntimeException.class, transactionManager = "primaryTransactionManager")
    public void sendMessageWithErrorPrimary() {

        //1.发送消息
        primaryTemplate.send("multi-kafka-topic", 0, "key", "primary-transaction-message")
                .addCallback(kafkaSendCallBackForString);

        //2.出现异常
        int errorNumber = 1 / 0;
    }

    public void sendMessageWithErrorSecondary() {

        //1.发送消息
        secondaryTemplate.send("multi-kafka-topic", 1, "key", "secondary-transaction-message")
                .addCallback(kafkaSendCallBackForString);

        //2.出现异常
        int errorNumber = 1 / 0;
    }
}
