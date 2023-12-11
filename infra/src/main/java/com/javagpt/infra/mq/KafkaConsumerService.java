package com.javagpt.infra.mq;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerService {

    @KafkaListener(topics = "testTopic", groupId = "test-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}

