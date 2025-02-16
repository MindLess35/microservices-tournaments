package com.microservices.user.dto.user;


import com.microservices.user.enums.Gender;
import com.microservices.user.validation.annotation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserUpdateDto(
        @NotBlank
        @Size(max = 64)
        String firstName,
        @NotBlank
        @Size(max = 64)
        String lastName,
        @Size(max = 512)
        String about,
        Gender gender,
        @Size(max = 255)
        @UniqueEmail
        @Email
        String email) {
}
