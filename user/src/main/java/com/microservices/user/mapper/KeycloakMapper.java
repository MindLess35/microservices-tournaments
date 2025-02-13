package com.microservices.user.mapper;

import com.microservices.user.dto.UserCreateDto;
import com.microservices.user.dto.UserLoginDto;
import com.microservices.user.property.KeycloakProperties;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface KeycloakMapper {

    default Map<String, String> toForm(KeycloakProperties keycloakProperties, UserLoginDto userLoginDto) {
        Map<String, String> form = new HashMap<>();
        form.put("client_id", keycloakProperties.userClientId());
        form.put("client_secret", keycloakProperties.userClientSecret());
        form.put("username", userLoginDto.username());
        form.put("password", userLoginDto.password());
        form.put("grant_type", keycloakProperties.grantType());
        return form;
    }

    @Mapping(target = "credentials", source = "password")
    @Mapping(target = "enabled", constant = "true")
    UserRepresentation toKeycloakUser(UserCreateDto userCreateDto);

    default List<CredentialRepresentation> mapPasswordToCredentials(String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        return Collections.singletonList(credential);
    }
}
