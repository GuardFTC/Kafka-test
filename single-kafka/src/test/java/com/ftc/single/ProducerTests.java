package com.ftc.single;

import com.ftc.single.config.KafkaSendCallBackForString;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
class ProducerTests {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaSendCallBackForString kafkaSendCallBackForString;

    /**
     * 测试主题
     */
    private static final String TEST_TOPIC = "single-kafka-topic";

    /**
     * 测试Key
     */
    private static final String TEST_KEY = "single-kafka-key";

    @Test
    void sendMessage() {

        //1.发送消息,指定主题
        kafkaTemplate.send(TEST_TOPIC, "topic_message")
                .addCallback(kafkaSendCallBackForString);

        //2.发送消息,指定主题、Key | 取模之后分区编号=0
        kafkaTemplate.send(TEST_TOPIC, TEST_KEY, "topic_key_message")
                .addCallback(kafkaSendCallBackForString);

        //3.发送消息,指定主题、分区、Key
        kafkaTemplate.send(TEST_TOPIC, 0, TEST_KEY, "topic_partition_key_message")
                .addCallback(kafkaSendCallBackForString);

        //4.发送消息,指定主题、分区、Key、时间戳
        kafkaTemplate.send(TEST_TOPIC, 0, System.currentTimeMillis(), TEST_KEY, "topic_partition_key_timestamp_message")
                .addCallback(kafkaSendCallBackForString);

        //5.发送消息,通过ProducerRecord形式
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TEST_TOPIC, 0, TEST_KEY, "record_message");
        kafkaTemplate.send(producerRecord)
                .addCallback(kafkaSendCallBackForString);
    }
}
