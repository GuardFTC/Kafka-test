package com.ftc.transactionkafka.producer;

import com.ftc.transactionkafka.config.KafkaSendCallBackForString;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-19 18:07:06
 * @describe: 生产者
 */
@Component
@Transactional(rollbackFor = RuntimeException.class)
public class Producer {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Resource
    private KafkaSendCallBackForString kafkaSendCallBackForString;

    public void sendMessageNoError() {
        kafkaTemplate.send("transaction-topic", "transaction-message")
                .addCallback(kafkaSendCallBackForString);
    }

    public void sendMessageWithError() {

        //1.发送消息
        kafkaTemplate.send("transaction-topic", "transaction-message")
                .addCallback(kafkaSendCallBackForString);

        //2.异常
        int errorNumber = 1 / 0;
    }
}
