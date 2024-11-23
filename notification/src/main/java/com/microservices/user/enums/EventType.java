package com.microservices.user.enums;

public enum EventType {
    TOURNAMENT_APPROVED,
    TOURNAMENT_REGISTRATION,
    TOURNAMENT_STARTED,
    TOURNAMENT_ENDED,

    MATCH_SCHEDULED,
    MATCH_REMINDER,
    MATCH_RESULT,

    PASSWORD_RESET,
    ACCOUNT_ACTIVATION,
    EMAIL_CHANGED,

    SYSTEM_ALERT,
    NEWSLETTER
}
