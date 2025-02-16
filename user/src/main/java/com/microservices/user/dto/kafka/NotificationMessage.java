package com.microservices.user.dto.kafka;

import com.microservices.user.enums.NotificationType;

public record NotificationMessage(
        String username,

        String firstName,

        String lastName,

        String email,

        NotificationType notificationType) {
}
