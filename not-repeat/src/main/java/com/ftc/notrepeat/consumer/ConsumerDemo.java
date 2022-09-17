package com.ftc.notrepeat.consumer;

import cn.hutool.core.util.ObjectUtil;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-13 10:01:26
 * @describe: 消费者demo
 */
@Component
public class ConsumerDemo {

    @KafkaListener(topics = {"not-repeat-message-topic"}, groupId = "${spring.kafka.consumer.group-id}")
    public void consumer1(ConsumerRecord<String, String> record, Consumer<String, String> consumer) {
        doMessage(record, consumer);
    }

    @KafkaListener(topics = {"not-repeat-message-topic"}, groupId = "${spring.kafka.consumer.group-id}")
    public void consumer2(ConsumerRecord<String, String> record, Consumer<String, String> consumer) {
        doMessage(record, consumer);
    }


    /**
     * 是否进行控制台打印
     */
    boolean isConsole = true;

    /**
     * 消费消息
     *
     * @param record   消息
     * @param consumer 消费者，用于手动提交偏移量
     */
    @SneakyThrows(InterruptedException.class)
    private void doMessage(ConsumerRecord<String, String> record, Consumer<String, String> consumer) {

        //1.消费消息
        if (isConsole) {
            System.out.println("receive message:" + record.value());
        } else {
            System.out.println("message is repeat:" + record.value());
        }

        //2.第一批消息睡100ms,第二批消息睡30s
        Integer longTimeMessage = 5;
        if (!longTimeMessage.equals(Integer.parseInt(record.value()))) {
            TimeUnit.MILLISECONDS.sleep(100);
        } else {
            isConsole = false;
            TimeUnit.SECONDS.sleep(30);
        }

        //3.手动异步提交偏移量
        consumer.commitAsync((offsets, exception) -> {

            //4.异常不为空,代表提交失败,改为同步提交偏移量,最大阻塞时间2000ms
            if (ObjectUtil.isNotNull(exception)) {
                consumer.commitSync(Duration.of(2000, ChronoUnit.MILLIS));
            }
        });
    }
}
