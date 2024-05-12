package org.javaacademy.online_bank.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.online_bank.config.BankProperty;
import org.javaacademy.online_bank.dto.OperationRefillDtoRq;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransfersToOtherBanksService {
    private final BankProperty bankProperty;
    private final RestTemplate restTemplate;

    public void transfersToOtherBank(OperationRefillDtoRq reqBody) {
        ResponseEntity<Void> response = restTemplate.postForEntity(
                bankProperty.getPartnerUrl() + "/operation/receive",
                reqBody,
                Void.class);
        if (response.getStatusCode() != HttpStatus.CREATED) {
            throw new RuntimeException("Transfer Failed!");
        }
    }



}
