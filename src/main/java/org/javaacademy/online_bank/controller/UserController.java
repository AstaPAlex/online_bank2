package org.javaacademy.online_bank.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.online_bank.dto.UserAuthDto;
import org.javaacademy.online_bank.dto.UserDto;
import org.javaacademy.online_bank.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public String signUp(@RequestBody UserDto userDto) {
        return userService.signUp(userDto);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/auth")
    public String auth(@RequestBody UserAuthDto authDto) {
        return userService.signIn(authDto.getNumberPhone(), authDto.getPinCode());
    }

}
