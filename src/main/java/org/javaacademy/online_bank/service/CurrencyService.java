package org.javaacademy.online_bank.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.online_bank.entity.Currency;
import org.javaacademy.online_bank.entity.ExchangeRate;
import org.javaacademy.online_bank.repository.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;
    private static final Integer SCALE_ROUND = 5;

    public void createExchangeRate(Currency currencyFrom, Currency currencyTo, BigDecimal rate) {
        if (Objects.equals(currencyFrom.getCurrency(), currencyTo.getCurrency())) {
            throw new RuntimeException("The currency should be different!");
        }
        if (rate.compareTo(BigDecimal.ZERO) < 1) {
            throw new RuntimeException("The course should be positive!");
        }
        currencyRepository.add(new ExchangeRate(currencyFrom, currencyTo, rate));
    }

    public BigDecimal findRate(Currency currency1, Currency currency2) {
        ExchangeRate exchangeRate = currencyRepository.findExchangeRates(currency1, currency2);
        if (!Objects.equals(exchangeRate.getCurrency1(), currency2)) {
            return exchangeRate.getRate().setScale(SCALE_ROUND, RoundingMode.CEILING);
        }
        return BigDecimal.ONE.divide(exchangeRate.getRate()).setScale(SCALE_ROUND, RoundingMode.CEILING);
    }

    public BigDecimal conversion(BigDecimal amount, Currency currencyFromConversion, Currency currencyToConversion) {
        if (Objects.equals(currencyFromConversion, currencyToConversion)) {
            return amount;
        }
        return amount.multiply(findRate(currencyFromConversion, currencyToConversion))
                .setScale(2, RoundingMode.CEILING);
    }
}
