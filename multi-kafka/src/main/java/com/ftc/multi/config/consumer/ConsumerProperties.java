package com.ftc.multi.config.consumer;

import lombok.Data;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-08 19:09:37
 * @describe: Consumer属性
 */
@Data
public class ConsumerProperties {

    /**
     * 服务器集群地址
     */
    private String bootstrapServers;

    /**
     * 消费者组ID
     */
    private String groupId;

    /**
     * 是否自动提交
     */
    private boolean enableAutoCommit;

    /**
     * Key反序列化策略类
     */
    private String keyDeserializer;

    /**
     * Value反序列化策略类
     */
    private String valueDeserializer;

    /**
     * 偏移量消费策略
     */
    private String autoOffsetReset;

    /**
     * 拉取消息超时时间/ms
     */
    private int pollTime;
}
