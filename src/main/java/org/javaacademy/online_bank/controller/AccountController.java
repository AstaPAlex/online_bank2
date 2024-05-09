package org.javaacademy.online_bank.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.online_bank.dto.AccountDto;
import org.javaacademy.online_bank.service.AccountService;
import org.javaacademy.online_bank.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<AccountDto> getAccountsByToken(@RequestParam String token) {
        return accountService.getAllAccountsByUser(userService.findUser(token));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/balance")
    public BigDecimal getBalance(@RequestParam String accountNumber,
                                 @RequestParam String token) {
        return accountService.getBalanceAccountByUser(accountNumber, userService.findUser(token));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String createAccount(@RequestBody Map<String, String> map) {
        return accountService.createAccountForUser(userService.findUser(map.get("token")));
    }

}
