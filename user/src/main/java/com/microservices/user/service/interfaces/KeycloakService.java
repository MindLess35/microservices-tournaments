package com.microservices.user.service.interfaces;


import com.microservices.user.dto.LoginResponseDto;
import com.microservices.user.dto.UserCreateDto;
import com.microservices.user.dto.UserLoginDto;

import java.util.UUID;

public interface KeycloakService {

    void deleteUser(UUID userId);

    UUID createUser(UserCreateDto userCreateDto);

    LoginResponseDto authenticate(UserLoginDto userLoginDto);
}