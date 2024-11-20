package com.microservices.user.dto;


import com.microservices.user.enums.Gender;
import com.microservices.user.validation.annotation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateDto(
        @Size(max = 512)
        String about,
        Gender gender,
        @Size(max = 255)
        @UniqueEmail
        @Email
        String email) {
}
