package org.javaacademy.online_bank.entity;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class Account {
    @NonNull
    private String number;
    @NonNull
    private User user;
    private BigDecimal balance = new BigDecimal(BigInteger.ZERO, 2);
}
