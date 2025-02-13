package com.microservices.user.dto;

import com.microservices.user.validation.annotation.Password;
import com.microservices.user.validation.annotation.Username;

public record UserLoginDto(

        @Username
        String username,

        @Password
        String password) {
}