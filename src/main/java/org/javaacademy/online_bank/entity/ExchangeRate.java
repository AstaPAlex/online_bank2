package org.javaacademy.online_bank.entity;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
public class ExchangeRate {
    @NonNull
    private final Currency currency1;
    @NonNull
    private final Currency currency2;
    @NonNull
    private BigDecimal rate;
}
