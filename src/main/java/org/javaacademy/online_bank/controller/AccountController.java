package org.javaacademy.online_bank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.online_bank.dto.AccountDto;
import org.javaacademy.online_bank.dto.CreateAccountDtoRq;
import org.javaacademy.online_bank.service.AccountService;
import org.javaacademy.online_bank.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Tag(name = "Account Controller", description = "Методы управления счетами!")
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    @ResponseStatus(OK)
    @GetMapping
    @Operation(summary = "Получить все счета по токену")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = AccountDto.class)))
    public List<AccountDto> getAccountsByToken(@RequestParam @Parameter(name = "Токен") String token) {
        return accountService.getAllAccountsByUser(userService.findUser(token));
    }

    @ResponseStatus(OK)
    @GetMapping("/balance")
    @Operation(summary = "Получить баланс счета")
    public BigDecimal getBalance(@RequestParam @Parameter(name = "Номер счета") String accountNumber,
                                 @RequestParam @Parameter(name = "Токен") String token) {
        return accountService.getBalanceAccountByUser(accountNumber, userService.findUser(token));
    }

    @ResponseStatus(CREATED)
    @PostMapping
    @Operation(summary = "Создать счет")
    public String createAccount(@RequestBody CreateAccountDtoRq createAccountDtoRq) {
        return accountService.createAccountForUser(
                userService.findUser(createAccountDtoRq.getToken()),
                createAccountDtoRq.getCurrency());
    }

}
