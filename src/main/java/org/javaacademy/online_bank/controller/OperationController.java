package org.javaacademy.online_bank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.javaacademy.online_bank.dto.OperationDtoRs;
import org.javaacademy.online_bank.dto.OperationPayDtoRq;
import org.javaacademy.online_bank.dto.OperationRefillDtoRq;
import org.javaacademy.online_bank.dto.TransferDtoRq;
import org.javaacademy.online_bank.entity.Currency;
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
@Tag(name = "Operation Controller", description = "Методы: пополнения, списания, перевода и получения списка операций")
public class OperationController {
    private final OperationService operationService;
    private final BankService bankService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(summary = "Получение всех операций по токену")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = OperationDtoRs.class)))
    public TreeSet<OperationDtoRs> getAllOperationsByToken(
            @RequestParam @Parameter(name = "Токен") String token) {
        return operationService.getAllOperationsByToken(token);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/pay")
    @Operation(summary = "Создать платеж")
    public void pay(@RequestBody OperationPayDtoRq operationPayDtoRq) {
        bankService.payment(operationPayDtoRq.getNumberAccount(),
                operationPayDtoRq.getAmount(),
                operationPayDtoRq.getDescription(),
                operationPayDtoRq.getToken(),
                Currency.getCodeCurrency(operationPayDtoRq.getCurrency()));
    }

    @Operation(summary = "Пополнить счет")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/receive")
    public void refill(@RequestBody OperationRefillDtoRq operationRefillDtoRq) {
        bankService.refill(
                operationRefillDtoRq.getNumberAccount(),
                operationRefillDtoRq.getAmount(),
                operationRefillDtoRq.getDescription(),
                Currency.getCodeCurrency(operationRefillDtoRq.getCurrency())
        );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/transfer")
    @Operation(summary = "Перевод в другой банк")
    public void transfer(@RequestBody TransferDtoRq transferDtoRq) {
        bankService.transferToOtherBank(
                transferDtoRq.getToken(),
                transferDtoRq.getAmount(),
                transferDtoRq.getDescription(),
                transferDtoRq.getNumberAccountUser(),
                transferDtoRq.getNumberAccountToSend(),
                transferDtoRq.getCurrency()
        );
    }

}
