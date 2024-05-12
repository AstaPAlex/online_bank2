package org.javaacademy.online_bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationBuyCurrencyDto {
    private String numberAccountFrom;
    private String numberAccountTo;
    private BigDecimal amount;
    private String token;
}
