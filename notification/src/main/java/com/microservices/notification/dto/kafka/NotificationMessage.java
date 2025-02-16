package com.microservices.notification.dto.kafka;

import com.microservices.notification.enums.NotificationType;

public record NotificationMessage(
        String username,

        String firstName,

        String lastName,

        String email,

        NotificationType notificationType) {
}