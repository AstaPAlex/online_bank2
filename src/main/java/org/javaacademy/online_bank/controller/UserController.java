package org.javaacademy.online_bank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.online_bank.dto.UserAuthDto;
import org.javaacademy.online_bank.dto.UserDto;
import org.javaacademy.online_bank.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Методы регистрации и аутентификации!")
public class UserController {
    private final UserService userService;

    @ResponseStatus(CREATED)
    @PostMapping("/signup")
    @Operation(summary = "Регистрация")
    public String signUp(@RequestBody UserDto userDto) {
        return userService.signUp(userDto);
    }

    @Operation(summary = "Аутентификации")
    @ResponseStatus(ACCEPTED)
    @PostMapping("/auth")
    public String auth(@RequestBody UserAuthDto authDto) {
        return userService.signIn(authDto.getNumberPhone(), authDto.getPinCode());
    }

}
