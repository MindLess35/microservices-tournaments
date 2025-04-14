package com.microservices.user.config;

import com.microservices.user.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String[] WHITE_LIST_URLS = {
            "/api/v1/users/sign-up",
            "/api/v1/users/sign-in",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/swagger-config",
            "/actuator/**"
    };
    private static final String ROLE_PREFIX = "ROLE_";
    private static final String REALM_ACCESS = "realm_access";
    private static final String ROLES = "roles";
    private static final String PREFERRED_USERNAME = "preferred_username";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)

                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverterForKeycloak())))

                .authorizeHttpRequests(request -> request
                        .requestMatchers(WHITE_LIST_URLS).permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasRole(Role.ROLE_ADMIN.getValue())
                        .requestMatchers("/api/v1/users/**").authenticated());

        return httpSecurity.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {
        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt -> {
            Map<String, Collection<String>> realmAccess = jwt.getClaim(REALM_ACCESS);
            Collection<String> roles = realmAccess.get(ROLES);
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role))
                    .collect(Collectors.toCollection(ArrayList::new));
        };
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        jwtAuthenticationConverter.setPrincipalClaimName(PREFERRED_USERNAME);
        return jwtAuthenticationConverter;
    }


}