package com.ftc.transactionkafka;

import com.ftc.transactionkafka.producer.Producer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransactionProducerTests {

    @Autowired
    private Producer producer;

    @Test
    void sendNoError() {
        producer.sendMessageNoError();
    }

    @Test
    void sendWithError() {
        producer.sendMessageWithError();
    }
}
