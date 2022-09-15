package com.ftc.ordermessage.consumer;

import com.ftc.ordermessage.service.ListenerService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-13 10:01:26
 * @describe: 顺序消息消费者V2
 */
@Component
@RequiredArgsConstructor
public class OrderMessageConsumerV2 {

    private final ListenerService listenerService;

    @KafkaListener(topics = {"order-message-topic-v2"}, groupId = "order-message-group-2")
    public void listenerN1(ConsumerRecord<String, String> record) {

        //1.打印消费者信息
        System.out.println("receive message N1");

        //2.消费消息
        consumerMessage(record);
    }

    @KafkaListener(topics = {"order-message-topic-v2"}, groupId = "order-message-group-2")
    public void listenerN2(ConsumerRecord<String, String> record) {

        //1.打印消费者信息
        System.out.println("receive message N2");

        //2.消费消息
        consumerMessage(record);
    }

    @KafkaListener(topics = {"order-message-topic-v2"}, groupId = "order-message-group-2")
    public void listenerN3(ConsumerRecord<String, String> record) {

        //1.打印消费者信息
        System.out.println("receive message N3");

        //2.消费消息
        consumerMessage(record);
    }

    /**
     * 消费消息
     *
     * @param record 消息
     */
    private void consumerMessage(ConsumerRecord<String, String> record) {

        //1.获取消息Key
        String key = record.key();

        //2.hash(Key)与总线程数取模
        int number = key.hashCode() % 3;

        //3.分配消息
        if (0 == number) {
            listenerService.doSomeThingNumber1(record);
        } else if (1 == number) {
            listenerService.doSomeThingNumber2(record);
        } else {
            listenerService.doSomeThingNumber3(record);
        }
    }
}
