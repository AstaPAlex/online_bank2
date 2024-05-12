package org.javaacademy.online_bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateDto {
    private String currency1;
    private String currency2;
    private BigDecimal rate;
}
