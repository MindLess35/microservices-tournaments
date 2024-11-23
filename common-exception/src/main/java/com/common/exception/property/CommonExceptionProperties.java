package com.common.exception.property;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "common.exception")
public record CommonExceptionProperties(
        boolean enabled) {
}
