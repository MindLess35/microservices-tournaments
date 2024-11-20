package com.microservices.user.validation.validator;

import com.microservices.user.repository.UserRepository;
import com.microservices.user.validation.annotation.UniqueUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private final UserRepository userRepository;
    private static final String USERNAME_PATTERN = "^[A-Za-zА-Яа-я0-9!()\\[\\]{}@#?$%^&*_-]{4,32}$";

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(username)) {
            return buildConstraintViolation(context, "Username cannot be blank");
        }

        if (!username.matches(USERNAME_PATTERN)) {
            return buildConstraintViolation(context, "Username must be between 4 and 32 characters," +
                                                     " contain only letters, numbers, special " +
                                                     "characters (()[]{}@#?$%^&*_-), and cannot contain spaces.");
        }

        if (userRepository.existsByUsername(username)) {
            return buildConstraintViolation(context, "Username is already taken");
        }
        return true;
    }

    private boolean buildConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }
}
