package com.microservices.user.service.interfaces;


import com.microservices.user.dto.auth.LoginResponseDto;
import com.microservices.user.dto.user.UserCreateDto;
import com.microservices.user.dto.auth.UserLoginDto;
import com.microservices.user.dto.user.UserReadDto;
import com.microservices.user.dto.user.UserUpdateDto;

public interface UserService {

    UserReadDto updateUser(Long id, UserUpdateDto userUpdateDto);

    void deleteUser(Long id);

//    void changePassword(Long id, ChangePasswordDto passwordDto);

    UserReadDto findById(Long id);

    UserReadDto createUser(UserCreateDto userCreateDto);

    LoginResponseDto authenticate(UserLoginDto userLoginDto);
}