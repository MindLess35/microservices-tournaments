package com.microservices.user.mapper;

import com.microservices.user.dto.UserCreateDto;
import com.microservices.user.dto.UserReadDto;
import com.microservices.user.dto.UserUpdateDto;
import com.microservices.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserCreateDto dto);

    User updateEntity(UserUpdateDto userUpdateDto, @MappingTarget User user);

    UserReadDto toDto(User user);

}
