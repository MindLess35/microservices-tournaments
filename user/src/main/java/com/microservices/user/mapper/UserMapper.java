package com.microservices.user.mapper;

import com.microservices.user.dto.UserCreateDto;
import com.microservices.user.dto.UserReadDto;
import com.microservices.user.dto.UserUpdateDto;
import com.microservices.user.entity.User;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserCreateDto dto);

    User updateEntity(UserUpdateDto userUpdateDto, @MappingTarget User user);

    UserReadDto toDto(User user);



}
