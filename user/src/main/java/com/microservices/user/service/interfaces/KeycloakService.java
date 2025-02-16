package com.microservices.user.service.interfaces;


import com.microservices.user.dto.auth.LoginResponseDto;
import com.microservices.user.dto.user.UserCreateDto;
import com.microservices.user.dto.auth.UserLoginDto;

import java.util.UUID;

public interface KeycloakService {

    void deleteUser(UUID userId);

    UUID createUser(UserCreateDto userCreateDto);

    LoginResponseDto authenticate(UserLoginDto userLoginDto);
}