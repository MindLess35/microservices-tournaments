package com.microservices.match.dto.kafka;


import com.microservices.match.enums.NotificationType;

public record NotificationMessage(
        String username,

        String firstName,

        String lastName,

        String email,

        NotificationType notificationType) {
}
