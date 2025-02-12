package com.microservices.user.service.impl;

import com.common.exception.exception.base.NotFoundBaseException;
import com.microservices.user.dto.UserCreateDto;
import com.microservices.user.dto.UserReadDto;
import com.microservices.user.dto.UserUpdateDto;
import com.microservices.user.entity.User;
import com.microservices.user.event.UserCreatedEvent;
import com.microservices.user.mapper.UserMapper;
import com.microservices.user.repository.UserRepository;
import com.microservices.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final KeycloakServiceImpl keycloakServiceImpl;
    private final ApplicationEventPublisher eventPublisher;
    private static final String USER_NOT_FOUND = "User with id [%d] not found";

    @SuppressWarnings({"OptionalGetWithoutIsPresent", "java:S3655"})
    @Override
    @Transactional
    public UserReadDto createUser(UserCreateDto userCreateDto) {
        UUID userUuid = keycloakServiceImpl.createUser(userCreateDto);
        return Optional.of(userCreateDto)
                .map(userMapper::toEntity)
                .map(u -> {
                    u.setKeycloakUuid(userUuid);
                    return u;
                })
                .map(userRepository::save)
                .map(userMapper::toDto)
                .map(dto -> {
                    eventPublisher.publishEvent(new UserCreatedEvent(userUuid));
                    return dto;
                })
                .get();
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleUserCreationRollback(UserCreatedEvent event) {
        keycloakServiceImpl.deleteUser(event.userUuid());
    }

    @Override
    public UserReadDto findById(Long id) {
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundBaseException(USER_NOT_FOUND.formatted(id))));
    }

    @Override
    @Transactional
    public UserReadDto updateUser(Long id, UserUpdateDto dto) {
        return userRepository.findById(id)
                .map(u -> userMapper.updateEntity(dto, u))
                .map(userRepository::saveAndFlush)
                .map(userMapper::toDto)
                .orElseThrow(() -> new NotFoundBaseException(USER_NOT_FOUND.formatted(id)));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundBaseException(USER_NOT_FOUND.formatted(id)));
        userRepository.delete(user);
        keycloakServiceImpl.deleteUser(user.getKeycloakUuid());
    }

//    @Override
//    @Transactional
//    public void changePassword(Long id, ChangePasswordDto passwordDto) {
//        if (!passwordDto.newPassword().equals(passwordDto.confirmationPassword()))
//            throw new InvalidPasswordException("passwordConfirmation", "New password and its confirmation do not match");
//
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new NotFoundBaseException(USER_NOT_FOUND.formatted(id)));
//
//        if (!passwordEncoder.matches(passwordDto.currentPassword(), user.getPassword()))
//            throw new InvalidPasswordException("wrongPassword", "Wrong password");
//
//        userRepository.updatePassword(id, passwordEncoder.encode(passwordDto.newPassword()));
//    }

}