package com.microservices.user.validation.validator;

import com.microservices.user.validation.annotation.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 64;
    private static final Pattern LOWER_CASE = Pattern.compile("[a-zа-я]");
    private static final Pattern UPPER_CASE = Pattern.compile("[A-ZА-Я]");
    private static final Pattern DIGIT = Pattern.compile("\\d");
    private static final Pattern SPECIAL_CHAR = Pattern.compile("[{}()\\[\\]!?@#$%^&*_\\-]");
    private static final Pattern NO_WHITESPACE = Pattern.compile("^\\S*$");

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(password)) {
            return buildConstraintViolation(context, "Password cannot be blank");
        }

        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            return buildConstraintViolation(context, String.format("Password must be between %d and %d characters long", MIN_LENGTH, MAX_LENGTH));
        }

        if (!NO_WHITESPACE.matcher(password).matches()) {
            return buildConstraintViolation(context, "Password cannot contain spaces");
        }
//          закомментировал, чтобы можно было использовать обычный пароль, а не добавлять к нему каждый раз символы
//          при 400 статусе из-за этих проверок

//        if (!LOWER_CASE.matcher(password).find()) {
//           return  buildConstraintViolation(context, "Password must contain at least one lowercase letter");
//        }
//
//        if (!UPPER_CASE.matcher(password).find()) {
//           return  buildConstraintViolation(context, "Password must contain at least one uppercase letter");
//        }
//
//        if (!DIGIT.matcher(password).find()) {
//           return  buildConstraintViolation(context, "Password must contain at least one digit");
//        }
//
//        if (!SPECIAL_CHAR.matcher(password).find()) {
//            return buildConstraintViolation(context, "Password must contain at least one " +
//                                              "special character ({}()[]?!@#$%^&*_\\-)");
//        }

        return true;
    }

    private boolean buildConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }
}