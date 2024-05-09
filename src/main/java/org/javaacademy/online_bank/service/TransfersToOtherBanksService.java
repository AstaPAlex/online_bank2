package org.javaacademy.online_bank.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.online_bank.config.BankProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransfersToOtherBanksService {
    private final BankProperty bankProperty;
    private final RestTemplate restTemplate;

    public void transfersToOtherBank(String name, BigDecimal amount, String description,
                                     String fullName, String numberAccount) {
        Map<String, String> jsonRq = new HashMap<>();
        jsonRq.put("amount", amount.toString());
        jsonRq.put("numberAccount", numberAccount);
        jsonRq.put("description", "Из банка: %s, от %s, описание: %s".formatted(name, fullName, description));
        ResponseEntity<Void> response = restTemplate.postForEntity(
                bankProperty.getPartnerUrl() + "/operation/receive",
                jsonRq,
                Void.class);
        if (response.getStatusCode() != HttpStatus.CREATED) {
            throw new RuntimeException("Transfer Failed!");
        }
    }



}
