package com.microservices.user.event;

import java.util.UUID;

public record UserCreatedEvent(UUID userUuid) {
}
