package com.microservices.user.service.impl;

import com.common.exception.exception.base.ConflictBaseException;
import com.common.exception.exception.base.ForbiddenBaseException;
import com.common.exception.exception.base.NotFoundBaseException;
import com.common.exception.exception.base.UnauthorizedBaseException;
import com.microservices.user.client.KeycloakFeignClient;
import com.microservices.user.dto.LoginResponseDto;
import com.microservices.user.dto.UserCreateDto;
import com.microservices.user.dto.UserLoginDto;
import com.microservices.user.mapper.KeycloakMapper;
import com.microservices.user.property.KeycloakProperties;
import com.microservices.user.service.interfaces.KeycloakService;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {
    private final Keycloak keycloak;
    private final KeycloakProperties keycloakProperties;
    private final KeycloakMapper keycloakMapper;
    private final KeycloakFeignClient keycloakFeignClient;

    @Override
    public UUID createUser(UserCreateDto userCreateDto) {
        UserRepresentation user = keycloakMapper.toKeycloakUser(userCreateDto);
        try (Response response = keycloak
                .realm(keycloakProperties.realm())
                .users()
                .create(user)) {

            int status = response.getStatus();
            if (status == 409)
                throw new ConflictBaseException(response.getStatusInfo().getReasonPhrase());

            String location = response.getHeaderString("Location");
            String userUuid = location.substring(location.lastIndexOf("/") + 1);
            return UUID.fromString(userUuid);

        } catch (ProcessingException ex) {
            Throwable cause = ex.getCause();
            if (cause instanceof NotFoundException) {
                throw new NotFoundBaseException(ex.getMessage(), ex);

            } else if (cause instanceof NotAuthorizedException) {
                throw new UnauthorizedBaseException(ex.getMessage(), ex);

            } else if (cause instanceof ForbiddenException) {
                throw new ForbiddenBaseException(ex.getMessage(), ex);
            }
            throw new RuntimeException("Ошибка при создании пользователя в Keycloak", ex);
        }
    }

    @Override
    public LoginResponseDto authenticate(UserLoginDto userLoginDto) {
        Map<String, String> form = keycloakMapper.toForm(keycloakProperties, userLoginDto);
        return keycloakFeignClient.authenticate(keycloakProperties.realm(), form);
    }

    @Override
    public void deleteUser(UUID userId) {
        try (Response response = keycloak
                .realm(keycloakProperties.realm())
                .users()
                .delete(userId.toString())) {
        }
    }

}

