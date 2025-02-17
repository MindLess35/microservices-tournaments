package com.microservices.user.service.impl;

import com.common.exception.exception.base.ConflictBaseException;
import com.common.exception.exception.base.ForbiddenBaseException;
import com.common.exception.exception.base.NotFoundBaseException;
import com.common.exception.exception.base.UnauthorizedBaseException;
import com.microservices.user.client.KeycloakFeignClient;
import com.microservices.user.dto.auth.LoginResponseDto;
import com.microservices.user.dto.user.UserCreateDto;
import com.microservices.user.dto.auth.UserLoginDto;
import com.microservices.user.mapper.KeycloakMapper;
import com.microservices.user.property.KeycloakProperties;
import com.microservices.user.service.interfaces.KeycloakService;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
        RealmResource realmResource = keycloak.realm(keycloakProperties.realm());
        UsersResource usersResource = realmResource.users();

        try (Response response = usersResource.create(user)) {
            int status = response.getStatus();
            if (status == 409)
                throw new ConflictBaseException(response.getStatusInfo().getReasonPhrase());

            String userUuid = CreatedResponseUtil.getCreatedId(response);
            RoleRepresentation roleRepresentation = realmResource
                    .roles()
                    .get(userCreateDto.role().getValue())
                    .toRepresentation();

            usersResource
                    .get(userUuid)
                    .roles()
                    .realmLevel()
                    .add(Collections.singletonList(roleRepresentation));

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

