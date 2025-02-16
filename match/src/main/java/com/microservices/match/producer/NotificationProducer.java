package com.microservices.match.producer;

import com.microservices.match.dto.kafka.NotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
public class NotificationProducer {
    private final KafkaTemplate<String, NotificationMessage> kafkaTemplate;

    public void sendNotification(NotificationMessage payload) {
        Message<NotificationMessage> message = MessageBuilder
                .withPayload(payload)
                .setHeader(TOPIC, "notifications")
                .build();

        kafkaTemplate.send(message);
    }
}