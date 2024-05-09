package org.javaacademy.online_bank.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.online_bank.dto.OperationPayDtoRq;
import org.javaacademy.online_bank.dto.OperationDtoRs;
import org.javaacademy.online_bank.dto.OperationRefillDtoRq;
import org.javaacademy.online_bank.service.BankService;
import org.javaacademy.online_bank.service.OperationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.TreeSet;

@RestController
@RequestMapping("/operation")
@RequiredArgsConstructor
public class OperationController {
    private final OperationService operationService;
    private final BankService bankService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public TreeSet<OperationDtoRs> getAllOperationsByToken(@RequestParam String token) {
        return operationService.getAllOperationsByToken(token);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/pay")
    public void pay(@RequestBody OperationPayDtoRq operationPayDtoRq) {
        bankService.payment(operationPayDtoRq.getNumberAccount(),
                operationPayDtoRq.getAmount(),
                operationPayDtoRq.getDescription(),
                operationPayDtoRq.getToken());
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/receive")
    public void refill(@RequestBody OperationRefillDtoRq operationRefillDtoRq) {
        bankService.refill(operationRefillDtoRq.getNumberAccount(),
                operationRefillDtoRq.getAmount(),
                operationRefillDtoRq.getDescription());
    }

}
