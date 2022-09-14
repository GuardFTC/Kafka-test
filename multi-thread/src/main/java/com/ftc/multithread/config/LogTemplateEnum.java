package com.ftc.multithread.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-07 16:24:20
 * @describe: 日志模板枚举
 */
@Getter
@AllArgsConstructor
public enum LogTemplateEnum {

    /**
     * Kafka消息发送成功日志模板
     */
    KAFKA_SEND_SUCCESS_LOG("Kafka producer send success! topic:{} partition:{} key:{} value:{}"),

    /**
     * Kafka消息发送失败日志模板
     */
    KAFKA_SEND_ERROR_LOG("Kafka producer send error! topic:{} partition:{} key:{} value:{}"),
    ;

    private final String template;
}
