package com.ftc.notrepeat.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-07 16:13:57
 * @describe: Kafka发布消息回调处理逻辑实现类
 */
@Component
@RequiredArgsConstructor
public class KafkaSendCallBackForString implements KafkaSendCallback<String, String> {

    private final KafkaTemplate<String, String> kafkaTemplate;

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

        //4.重发消息
        resendFailMessage(failedProducerRecord);
    }

    /**
     * 消息重发
     *
     * @param producerRecord 消息
     */
    private void resendFailMessage(ProducerRecord<String, String> producerRecord) {

        //1.获取重试次数 TODO 后续可优化为从缓存中获取
        int retryTime = 0;

        //2.获取最大重试次数 TODO 后续可优化为写入配置文件中
        int maxRetryTime = 10;

        //3.重试次数++
        retryTime++;

        //4.判定是否超过限制
        if (retryTime > maxRetryTime) {
            String errorMessage = StrUtil.format(LogTemplateEnum.KAFKA_SEND_OVER_TIME_LIMIT.getTemplate(), JSONUtil.toJsonStr(producerRecord));
            throw new RuntimeException(errorMessage);
        }

        //5.未超过限制重发消息
        kafkaTemplate.send(producerRecord).addCallback(this);
    }
}
