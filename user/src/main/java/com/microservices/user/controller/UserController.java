package com.microservices.user.controller;

import com.microservices.user.dto.auth.LoginResponseDto;
import com.microservices.user.dto.user.UserCreateDto;
import com.microservices.user.dto.auth.UserLoginDto;
import com.microservices.user.dto.user.UserReadDto;
import com.microservices.user.dto.user.UserUpdateDto;
import com.microservices.user.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private static final String X_REFRESH_TOKEN = "X-Refresh-Token";

    @PostMapping("/sign-up")
    public ResponseEntity<UserReadDto> register(@RequestBody @Validated UserCreateDto userCreateDto) {
        return ResponseEntity.status(CREATED).body(userService.createUser(userCreateDto));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> login(@RequestBody @Validated UserLoginDto userLoginDto) {
        LoginResponseDto dto = userService.authenticate(userLoginDto);
        String tokenTypeWithWhitespace = dto.tokenType() + " ";
        String cookie = String.format(X_REFRESH_TOKEN + "=%s; HttpOnly; Secure; Path=/; Max-Age=%d",
                tokenTypeWithWhitespace + dto.refreshToken(), dto.refreshExpiresIn());

        return ResponseEntity
                .status(OK)
                .header(AUTHORIZATION, tokenTypeWithWhitespace + dto.accessToken())
                .header(SET_COOKIE, cookie)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserReadDto> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserReadDto> updateUser(@PathVariable("id") Long id,
                                                  @RequestBody @Validated UserUpdateDto userUpdateDto) {
        return ResponseEntity.ok(userService.updateUser(id, userUpdateDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

//    @Operation(summary = "Changes the user's password")
//    @PatchMapping("{id}/password")
//    public ResponseEntity<HttpStatus> changePassword(@PathVariable("id") Long id,
//                                                     @RequestBody @Validated ChangePasswordDto changePasswordDto) {
//        userService.changePassword(id, changePasswordDto);
//        return ResponseEntity.ok().build();
//    }

}