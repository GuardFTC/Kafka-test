package com.ftc.multi.config.producer;

import lombok.Data;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-08 13:39:47
 * @describe: Producer配置属性
 */
@Data
public class ProducerProperties {

    /**
     * 服务器集群地址
     */
    private String bootstrapServers;

    /**
     * ACK参数
     */
    private String ack;

    /**
     * Key序列化策略类
     */
    private String keySerializer;

    /**
     * Value序列化策略类
     */
    private String valueSerializer;

    /**
     * 重试次数
     */
    private int retries;

    /**
     * 是否开启幂等
     */
    private boolean idempotence;
}
