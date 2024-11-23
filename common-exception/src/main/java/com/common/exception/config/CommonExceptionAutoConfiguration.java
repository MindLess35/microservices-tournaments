package com.common.exception.config;

import com.common.exception.exceptionhandler.GlobalExceptionHandler;
import com.common.exception.property.CommonExceptionProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CommonExceptionProperties.class)
@ConditionalOnProperty(prefix = "common.exception", name = "enabled", havingValue = "true", matchIfMissing = true)
public class CommonExceptionAutoConfiguration {

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

}
