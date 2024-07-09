package com.example.kafkaproducer.service.impl;

import com.example.kafkaproducer.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerServiceImpl implements KafkaProducerService {

    @Value("${kafka.topic-name}")
    private String topicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendMessage(String message) {
        kafkaTemplate.send(topicName, message).whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Message sent to topic: {}", message);
            } else {
                log.error("Failed to send message", ex);
            }
        });
    }
}
