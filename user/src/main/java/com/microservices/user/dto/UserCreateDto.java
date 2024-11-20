package com.microservices.user.dto;

import com.microservices.user.enums.Role;
import com.microservices.user.validation.annotation.Password;
import com.microservices.user.validation.annotation.UniqueUsernameAndEmail;
import com.microservices.user.validation.annotation.Username;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@UniqueUsernameAndEmail
public record UserCreateDto(
        @Size(max = 64)
        @Username
        String username,

        @Email
        @Size(max = 255)
        String email,

        @NotNull
        Role role,

        @Password
        String password) {
}