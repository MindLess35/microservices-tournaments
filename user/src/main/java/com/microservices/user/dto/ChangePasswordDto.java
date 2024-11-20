package com.microservices.user.dto;

import com.microservices.user.validation.annotation.Password;

public record ChangePasswordDto(
        @Password
        String currentPassword,
        @Password
        String newPassword,
        @Password
        String confirmationPassword) {
}