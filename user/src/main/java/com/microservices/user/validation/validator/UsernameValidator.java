package com.microservices.user.validation.validator;

import com.microservices.user.validation.annotation.Username;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class UsernameValidator implements ConstraintValidator<Username, String> {
    private static final String USERNAME_PATTERN = "^[A-Za-zА-Яа-я0-9(){}\\[\\]!@#$%^&*_-]{4,64}$";

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(username)) {
            return buildConstraintViolation(context, "Username cannot be blank");
        }

        if (!username.matches(USERNAME_PATTERN)) {
            return buildConstraintViolation(context, "Username must be between 4 and 32 characters," +
                                                     " contain only letters, numbers, special " +
                                                     "characters ([](){}!@#$%^&*_-), and cannot contain spaces.");
        }
        return true;
    }

    private boolean buildConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }
}


