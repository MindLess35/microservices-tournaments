package com.microservices.user.client;

import com.microservices.user.dto.auth.LoginResponseDto;
import io.micrometer.core.annotation.Timed;
import liquibase.Liquibase;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "keycloak", url = "${keycloak.auth-server-url}")
public interface KeycloakFeignClient {

    @Timed("authenticateInKeycloak")
    @PostMapping(
            value = "/realms/{realm}/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    LoginResponseDto authenticate(@PathVariable("realm") String realm, Map<String, ?> form);

}