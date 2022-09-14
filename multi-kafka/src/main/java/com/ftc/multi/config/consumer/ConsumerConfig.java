package com.ftc.multi.config.consumer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-08 19:14:05
 * @describe: Consumer配置
 */
@Configuration
public class ConsumerConfig {

    @Bean("primaryConsumerProperties")
    @ConfigurationProperties("kafka.primary.consumer")
    public ConsumerProperties primaryProperties() {
        return new ConsumerProperties();
    }

    @Bean("secondaryConsumerProperties")
    @ConfigurationProperties("kafka.secondary.consumer")
    public ConsumerProperties secondaryProperties() {
        return new ConsumerProperties();
    }
}
