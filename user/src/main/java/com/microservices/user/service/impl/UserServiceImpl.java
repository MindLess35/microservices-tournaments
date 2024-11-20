package com.microservices.user.service.impl;

import com.common.exception.exception.base.ResourceNotFoundException;
import com.microservices.user.dto.ChangePasswordDto;
import com.microservices.user.dto.UserCreateDto;
import com.microservices.user.dto.UserReadDto;
import com.microservices.user.dto.UserUpdateDto;
import com.microservices.user.entity.User;
import com.microservices.user.exception.InvalidPasswordException;
import com.microservices.user.mapper.UserMapper;
import com.microservices.user.repository.UserRepository;
import com.microservices.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private static final String USER_NOT_FOUND = "User with id [%d] not found";

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    @Transactional
    public UserReadDto createUser(UserCreateDto userCreateDto) {
        return Optional.of(userCreateDto)
                .map(userMapper::toEntity)
                .map(u -> {
                    u.setPassword(passwordEncoder.encode(u.getPassword()));
                    return u;
                })
                .map(userRepository::save)
                .map(userMapper::toDto)
                .get();
    }

    @Override
    public UserReadDto findById(Long id) {
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND.formatted(id))));
    }

    @Override
    @Transactional
    public UserReadDto updateUser(Long id, UserUpdateDto dto) {
        return userRepository.findById(id)
                .map(u -> userMapper.updateEntity(dto, u))
                .map(userRepository::save)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND.formatted(id)));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.delete(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND.formatted(id))));
    }

    @Override
    @Transactional
    public void changePassword(Long id, ChangePasswordDto passwordDto) {
        if (!passwordDto.newPassword().equals(passwordDto.confirmationPassword()))
            throw new InvalidPasswordException("passwordConfirmation", "New password and its confirmation do not match");

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND.formatted(id)));

        if (!passwordEncoder.matches(passwordDto.currentPassword(), user.getPassword()))
            throw new InvalidPasswordException("wrongPassword", "Wrong password");

        userRepository.updatePassword(id, passwordEncoder.encode(passwordDto.newPassword()));
    }

}