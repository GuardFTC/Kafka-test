package com.ftc.multi.config.consumer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-08 16:00:24
 * @describe: Consumer客户端配置
 */
@EnableKafka
@Configuration
@RequiredArgsConstructor
public class ConsumerClientConfig {

    private final com.ftc.multi.config.consumer.ConsumerConfig consumerConfig;

    @Bean("primaryConsumerFactory")
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> primaryConsumerFactory() {

        //1.获取属性
        ConsumerProperties consumerProperties = consumerConfig.primaryProperties();

        //2.获取配置
        Map<String, Object> props = getProps(consumerProperties);

        //3.创建工厂
        DefaultKafkaConsumerFactory<Object, Object> consumerFactory = new DefaultKafkaConsumerFactory<>(props);

        //4.创建最终工厂
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setPollTimeout(consumerProperties.getPollTime());

        //5.返回
        return factory;
    }

    @Bean("secondaryConsumerFactory")
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> secondaryConsumerFactory() {

        //1.获取属性
        ConsumerProperties secondaryProperties = consumerConfig.secondaryProperties();

        //2.获取配置
        Map<String, Object> props = getProps(secondaryProperties);

        //3.创建工厂
        DefaultKafkaConsumerFactory<Object, Object> consumerFactory = new DefaultKafkaConsumerFactory<>(props);

        //4.创建最终工厂
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setPollTimeout(secondaryProperties.getPollTime());

        //5.返回
        return factory;
    }

    /**
     * 封装不同的配置
     *
     * @param consumerProperties Consumer属性
     * @return 封装后的配置
     */
    private Map<String, Object> getProps(ConsumerProperties consumerProperties) {

        //.1生成配置
        Map<String, Object> props = new HashMap<>(10);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerProperties.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerProperties.getGroupId());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, consumerProperties.isEnableAutoCommit());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumerProperties.getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumerProperties.getValueDeserializer());

        //2.返回
        return props;
    }
}
