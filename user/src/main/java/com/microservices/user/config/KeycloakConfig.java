package com.microservices.user.config;

import com.microservices.user.property.KeycloakProperties;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfig {
    private final KeycloakProperties keycloakProperties;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakProperties.authServerUrl())
                .realm(keycloakProperties.realm())
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(keycloakProperties.clientId())
                .clientSecret(keycloakProperties.clientSecret())
                .username(keycloakProperties.username())
                .password(keycloakProperties.password())
                .build();
    }
}
