package com.microservices.user.mapper;

import com.microservices.user.dto.user.UserCreateDto;
import com.microservices.user.dto.user.UserReadDto;
import com.microservices.user.dto.kafka.NotificationMessage;
import com.microservices.user.dto.user.UserUpdateDto;
import com.microservices.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserCreateDto dto);

    User updateEntity(UserUpdateDto userUpdateDto, @MappingTarget User user);

    UserReadDto toDto(User user);

    @Mapping(target = "notificationType", expression = "java(com.microservices.user.enums.NotificationType.USER_REGISTRATION)")
    NotificationMessage toNotificationMessage(UserReadDto dto);
}
