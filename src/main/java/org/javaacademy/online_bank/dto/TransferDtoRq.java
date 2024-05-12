package org.javaacademy.online_bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javaacademy.online_bank.entity.Currency;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferDtoRq {
    private String token;
    private BigDecimal amount;
    private String description;
    private String numberAccountUser;
    private String numberAccountToSend;
    private Currency currency;
}
