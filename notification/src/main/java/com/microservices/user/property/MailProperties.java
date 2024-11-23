package com.microservices.user.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.Properties;

@ConfigurationProperties(prefix = "spring.mail")
public record MailProperties(
        String host,

        Integer port,

        String username,

        String password,

        Properties properties) {
}