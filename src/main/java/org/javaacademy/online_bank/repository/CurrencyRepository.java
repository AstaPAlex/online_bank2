package org.javaacademy.online_bank.repository;

import org.javaacademy.online_bank.entity.Currency;
import org.javaacademy.online_bank.entity.ExchangeRate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component

public class CurrencyRepository {
    private final Set<ExchangeRate> exchangeRates = new HashSet<>();

    public void add(ExchangeRate exchangeRate) {
        exchangeRates.add(exchangeRate);
    }

    public ExchangeRate findExchangeRates(Currency currency1, Currency currency2) {
        return exchangeRates.stream()
                .filter(exchangeRate ->
                        Objects.equals(exchangeRate.getCurrency1(), currency1)
                                || Objects.equals(exchangeRate.getCurrency2(), currency1))
                .filter(exchangeRate ->
                        Objects.equals(exchangeRate.getCurrency1(), currency2)
                                || Objects.equals(exchangeRate.getCurrency2(), currency2))
                .findFirst()
                .orElseThrow();
    }
}
