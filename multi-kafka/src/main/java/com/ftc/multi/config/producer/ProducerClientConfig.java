package com.ftc.multi.config.producer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.transaction.KafkaTransactionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-08 13:42:00
 * @describe: Producer客户端配置
 */
@Configuration
@RequiredArgsConstructor
public class ProducerClientConfig {

    private final com.ftc.multi.config.producer.ProducerConfig producerConfig;

    @Bean("primaryProducerTemplate")
    public KafkaTemplate<String, String> primaryTemplate() {
        return new KafkaTemplate<>(primaryFactory());
    }

    @Bean("primaryTransactionManager")
    public KafkaTransactionManager<String, String> primaryTransactionManager() {
        return new KafkaTransactionManager<>(primaryFactory());
    }
    @Bean("primaryFactory")
    public DefaultKafkaProducerFactory<String, String> primaryFactory() {

        //1.获取配置
        ProducerProperties primaryProperties = producerConfig.primaryProperties();

        //2.获取配置
        Map<String, Object> props = getProps(primaryProperties);

        //3.创建工厂
        DefaultKafkaProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(props);

        //4.开启事务
        producerFactory.setTransactionIdPrefix(primaryProperties.getTransactionIdPrefix());

        //5.返回
        return producerFactory;
    }

    @Bean("secondaryProducerTemplate")
    public KafkaTemplate<String, String> secondaryTemplate() {
        return new KafkaTemplate<>(secondaryFactory());
    }

    @Bean("secondaryFactory")
    public DefaultKafkaProducerFactory<String, String> secondaryFactory() {

        //1.获取配置
        ProducerProperties secondaryProperties = producerConfig.secondaryProperties();

        //2.获取配置
        Map<String, Object> props = getProps(secondaryProperties);

        //3.创建工厂,返回
        return new DefaultKafkaProducerFactory<>(props);
    }

    /**
     * 封装不同的配置
     *
     * @param producerProperties Producer属性
     * @return 封装后的配置
     */
    private Map<String, Object> getProps(ProducerProperties producerProperties) {

        //1.生成配置
        Map<String, Object> props = new HashMap<>(10);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerProperties.getBootstrapServers());
        props.put(ProducerConfig.ACKS_CONFIG, producerProperties.getAck());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerProperties.getKeySerializer());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerProperties.getValueSerializer());
        props.put(ProducerConfig.RETRIES_CONFIG, producerProperties.getRetries());
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, producerProperties.isIdempotence());

        //2.返回
        return props;
    }
}
