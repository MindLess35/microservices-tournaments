package com.microservices.user.validation.validator;

import com.microservices.user.dto.UserCreateDto;
import com.microservices.user.projection.UsernameAndEmailProjection;
import com.microservices.user.repository.UserRepository;
import com.microservices.user.validation.annotation.UniqueUsernameAndEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UniqueUsernameAndEmailValidator implements ConstraintValidator<UniqueUsernameAndEmail, UserCreateDto> {
    private final UserRepository userRepository;

    @Override
    public boolean isValid(UserCreateDto dto, ConstraintValidatorContext context) {
        String usernameFromDto = dto.username();
        String emailFromDto = dto.email();
        List<UsernameAndEmailProjection> projections = userRepository.findExistingUsernameAndEmail(usernameFromDto, emailFromDto);

        boolean usernameExists = projections.stream()
                .anyMatch(projection -> usernameFromDto.equals(projection.getUsername()));
        boolean emailExists = projections.stream()
                .anyMatch(projection -> emailFromDto.equals(projection.getEmail()));

        if (usernameExists) {
            addViolation(context, "username", "Username is already taken");
        }
        if (emailExists) {
            addViolation(context, "email", "Email is already taken");
        }

        return !(usernameExists || emailExists);
    }

    private void addViolation(ConstraintValidatorContext context, String field, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(field)
                .addConstraintViolation();
    }
}


