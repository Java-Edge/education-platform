//package com.javaedge.infra.mq;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class KafkaProducerService {
//
//    @Autowired
//    private KafkaTemplate<String, Object> kafkaTemplate;
//
//    public void sendMessage(String topic, Object message) {
//        kafkaTemplate.send(topic, message);
//    }
//}
//
