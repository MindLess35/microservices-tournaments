package com.microservices.team.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "http")
public record ServiceUrlsProperties(
        String userServiceUrl) {

}