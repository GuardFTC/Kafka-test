package com.ftc.ordermessage.config;

import cn.hutool.log.StaticLog;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-07 16:13:57
 * @describe: Kafka发布消息回调处理逻辑实现类
 */
@Component
public class KafkaSendCallBackForString implements KafkaSendCallback<String, String> {

    @Override
    public void onSuccess(SendResult<String, String> result) {

        //1.获取消息属性
        ProducerRecord<String, String> producerRecord = result.getProducerRecord();
        String topic = producerRecord.topic();
        Integer partition = producerRecord.partition();
        String key = producerRecord.key();
        String value = producerRecord.value();

        //2.打印日志
        StaticLog.info(LogTemplateEnum.KAFKA_SEND_SUCCESS_LOG.getTemplate(), topic, partition, key, value);
    }

    @Override
    public void onFailure(KafkaProducerException e) {

        //1.获取消息属性
        ProducerRecord<String, String> failedProducerRecord = e.getFailedProducerRecord();
        String topic = failedProducerRecord.topic();
        Integer partition = failedProducerRecord.partition();
        String key = failedProducerRecord.key();
        String value = failedProducerRecord.value();

        //2.打印日志
        StaticLog.error(LogTemplateEnum.KAFKA_SEND_ERROR_LOG.getTemplate(), topic, partition, key, value);

        //3.异常堆栈信息输出
        e.printStackTrace();

        //4.TODO 可进行自定义的异常逻辑，比如重新发送消息等操作
    }
}
