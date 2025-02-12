package com.microservices.user.service.impl;

import com.common.exception.exception.base.ConflictBaseException;
import com.common.exception.exception.base.NotFoundBaseException;
import com.common.exception.exception.base.UnauthorizedBaseException;
import com.microservices.user.dto.UserCreateDto;
import com.microservices.user.property.KeycloakProperties;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl {
    private final Keycloak keycloak;
    private final KeycloakProperties keycloakProperties;

    public UUID createUser(UserCreateDto userCreateDto) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userCreateDto.username());
        user.setEmail(userCreateDto.email());
        user.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userCreateDto.password());
        credential.setTemporary(false);
        user.setCredentials(Collections.singletonList(credential));

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
            }
            throw new RuntimeException("Ошибка при создании пользователя в Keycloak", ex);
        }
    }

    public void deleteUser(UUID userId) {
        try (Response response = keycloak
                .realm(keycloakProperties.realm())
                .users()
                .delete(userId.toString())) {
        }
    }

}

