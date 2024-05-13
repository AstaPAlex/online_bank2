package org.javaacademy.online_bank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.online_bank.dto.ExchangeRateDto;
import org.javaacademy.online_bank.dto.OperationBuyCurrencyDto;
import org.javaacademy.online_bank.entity.Currency;
import org.javaacademy.online_bank.service.BankService;
import org.javaacademy.online_bank.service.CurrencyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
@Tag(name = "Currency Controller", description = "Операции с валютой!")
public class CurrencyController {
    private final CurrencyService currencyService;
    private final BankService bankService;

    @ResponseStatus(CREATED)
    @PostMapping
    @Operation(summary = "Создать курс валют")
    public void createExchangeRate(@RequestBody ExchangeRateDto exchangeRateDto) {
        currencyService.createExchangeRate(Currency.getCodeCurrency(exchangeRateDto.getCurrency1()),
                Currency.getCodeCurrency(exchangeRateDto.getCurrency2()),
                exchangeRateDto.getRate());
    }

    @ResponseStatus(CREATED)
    @PostMapping("/buy")
    @Operation(summary = "Купить валюту!")
    public void buyCurrency(@RequestBody OperationBuyCurrencyDto operationBuyCurrencyDto) {
        bankService.buyCurrency(
                operationBuyCurrencyDto.getNumberAccountFrom(),
                operationBuyCurrencyDto.getNumberAccountTo(),
                operationBuyCurrencyDto.getAmount(),
                operationBuyCurrencyDto.getToken()
        );
    }


}
