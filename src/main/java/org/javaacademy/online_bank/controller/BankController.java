package org.javaacademy.online_bank.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.online_bank.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank-info")
@RequiredArgsConstructor
public class BankController {
    private final BankService bankService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public String info() {
        return bankService.info();
    }


}
