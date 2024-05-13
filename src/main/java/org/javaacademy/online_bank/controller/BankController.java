package org.javaacademy.online_bank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.online_bank.service.BankService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/bank-info")
@RequiredArgsConstructor
@Tag(name = "Bank Controller", description = "Получение информации о банке!")
public class BankController {
    private final BankService bankService;

    @ResponseStatus(OK)
    @GetMapping
    @Operation(summary = "Получить имя банка")
    public String info() {
        return bankService.info();
    }


}
