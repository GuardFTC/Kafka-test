package com.ftc.notlossmessage.consumer;

import cn.hutool.core.util.ObjectUtil;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-13 10:01:26
 * @describe: 消费者demo
 */
@Component
public class ConsumerDemo {

    @KafkaListener(topics = {"not-loss-message-topic"}, groupId = "${spring.kafka.consumer.group-id}")
    public void failListener(ConsumerRecord<String, String> record, Consumer<String, String> consumer) {

        //1.消费消息
        System.out.println("receive message:" + record.value());

        //2.手动异步提交偏移量
        consumer.commitAsync((offsets, exception) -> {

            //3.异常不为空,代表提交失败,改为同步提交偏移量,最大阻塞时间2000ms
            if (ObjectUtil.isNotNull(exception)) {
                consumer.commitSync(Duration.of(100, ChronoUnit.MILLIS));
            }
        });
    }
}
