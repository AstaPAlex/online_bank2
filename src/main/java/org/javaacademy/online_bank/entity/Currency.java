package org.javaacademy.online_bank.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum Currency {
    RUB("Рубль", "810"), DOLLAR("Доллар", "840"), CNY("Юань", "378");
    private final String currency;
    private final String code;

    public static Currency getCodeCurrency(String currency) {
        return Arrays.stream(Currency.values())
                .filter(cur -> Objects.equals(cur.getCurrency(), currency))
                .findFirst()
                .orElseThrow();
    }

}
