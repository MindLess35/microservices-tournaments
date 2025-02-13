package com.microservices.user.service.interfaces;


import com.microservices.user.dto.LoginResponseDto;
import com.microservices.user.dto.UserCreateDto;
import com.microservices.user.dto.UserLoginDto;
import com.microservices.user.dto.UserReadDto;
import com.microservices.user.dto.UserUpdateDto;

public interface UserService {

    UserReadDto updateUser(Long id, UserUpdateDto userUpdateDto);

    void deleteUser(Long id);

//    void changePassword(Long id, ChangePasswordDto passwordDto);

    UserReadDto findById(Long id);

    UserReadDto createUser(UserCreateDto userCreateDto);

    LoginResponseDto authenticate(UserLoginDto userLoginDto);
}