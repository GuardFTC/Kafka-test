package com.ftc.multi.config.producer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-08 15:28:45
 * @describe: Producer配置
 */
@Configuration
public class ProducerConfig {

    @Bean(name = "primaryProducerProperties")
    @ConfigurationProperties(prefix = "kafka.primary.producer")
    public ProducerProperties primaryProperties() {
        return new ProducerProperties();
    }

    @Bean(name = "secondaryProducerProperties")
    @ConfigurationProperties(prefix = "kafka.secondary.producer")
    public ProducerProperties secondaryProperties() {
        return new ProducerProperties();
    }
}
