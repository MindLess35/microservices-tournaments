package com.microservices.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String[] WHITE_LIST_URLS = {
            "/api/v1/users/sign-up",
            "/api/v1/users/sign-in",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/swagger-config"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)

                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults()))

                .authorizeHttpRequests(request -> request
                        .requestMatchers(WHITE_LIST_URLS).permitAll()
                        .requestMatchers("/api/v1/users/**").authenticated());

        return httpSecurity.build();
    }

}