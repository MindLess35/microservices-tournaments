package com.microservices.notification.consumer;

import com.microservices.notification.dto.kafka.NotificationMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    @KafkaListener(topics = "notifications")
    public void consumeNotificationMessage(NotificationMessage notificationMessage) {
        log.error(notificationMessage.toString());

    }
}