package org.javaacademy.online_bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationPayDtoRq {
    private String token;
    private BigDecimal amount;
    private String numberAccount;
    private String description;
    private String currency;
}
