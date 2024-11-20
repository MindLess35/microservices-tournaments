package com.microservices.user.service;


import com.microservices.user.dto.ChangePasswordDto;
import com.microservices.user.dto.UserCreateDto;
import com.microservices.user.dto.UserReadDto;
import com.microservices.user.dto.UserUpdateDto;

public interface UserService {

    UserReadDto updateUser(Long id, UserUpdateDto userUpdateDto);

    void deleteUser(Long id);

    void changePassword(Long id, ChangePasswordDto passwordDto);

    UserReadDto findById(Long id);

    UserReadDto createUser(UserCreateDto userCreateDto);
}