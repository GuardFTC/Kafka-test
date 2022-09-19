package com.ftc.springkafkatest.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.Map;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-19 09:54:31
 * @describe: 嵌入式Kafka配置
 */
@Configuration
@RequiredArgsConstructor
public class EmbeddedKafkaConfig {

    private final EmbeddedKafkaBroker broker;

    @Bean("embeddedProducer")
    public KafkaTemplate<String, String> embeddedProducer() {

        //1.获取Producer配置
        Map<String, Object> producerProps = KafkaTestUtils.producerProps(broker);

        //2.返回Producer实例
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerProps));
    }

    @Bean("embeddedConsumerFactory")
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> embeddedConsumerFactory() {

        //1.定义基础属性
        String group = "embeddedGroup";
        String autoCommit = "true";

        //2.获取Consumer配置
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(group, autoCommit, broker);

        //3.创建工厂
        DefaultKafkaConsumerFactory<Object, Object> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);

        //4.创建Consumer工厂
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

        //5.返回
        return factory;
    }
}
