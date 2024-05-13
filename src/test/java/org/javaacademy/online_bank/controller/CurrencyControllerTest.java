package org.javaacademy.online_bank.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.javaacademy.online_bank.dto.ExchangeRateDto;
import org.javaacademy.online_bank.dto.OperationBuyCurrencyDto;
import org.javaacademy.online_bank.dto.OperationRefillDtoRq;
import org.javaacademy.online_bank.entity.Currency;
import org.javaacademy.online_bank.entity.ExchangeRate;
import org.javaacademy.online_bank.repository.AccountRepository;
import org.javaacademy.online_bank.repository.CurrencyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CurrencyControllerTest extends AbstractIntegrationTest {
    private static final String BASE_URL = "/currency";
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Test
    void createExchangeRateSuccess() {
        ExchangeRateDto exchangeRateDto = new ExchangeRateDto(Currency.DOLLAR.getCurrency(),
                Currency.RUB.getCurrency(), BigDecimal.valueOf(100));
        RestAssured
                .given()
                .body(exchangeRateDto)
                .contentType(ContentType.JSON)
                .log().all()
                .post(BASE_URL)
                .then()
                .statusCode(201);
        ExchangeRate exchangeRates = currencyRepository.findExchangeRates(Currency.DOLLAR, Currency.RUB);
        assertEquals(BigDecimal.valueOf(100), exchangeRates.getRate());
    }

    @Test
    void createExchangeRateFail() {
        RestAssured
                .given()
                .body(new ExchangeRateDto(Currency.DOLLAR.getCurrency(),
                        Currency.RUB.getCurrency(), BigDecimal.ZERO))
                .contentType(ContentType.JSON)
                .log().all()
                .post(BASE_URL)
                .then()
                .statusCode(503);

        RestAssured
                .given()
                .body(new ExchangeRateDto(Currency.DOLLAR.getCurrency(),
                        Currency.DOLLAR.getCurrency(), BigDecimal.TEN))
                .contentType(ContentType.JSON)
                .log().all()
                .post(BASE_URL)
                .then()
                .statusCode(503);
    }

    @Test
    void buyCurrencySuccess() {
        String token = getToken();
        String accountNumberRub = createAccount(token, RUB_CURRENCY);
        String accountNumberDollar = createAccount(token, Currency.DOLLAR.getCurrency());
        currencyRepository.add(new ExchangeRate(Currency.DOLLAR, Currency.RUB, BigDecimal.valueOf(100)));
        restTemplate.postForEntity(
                FULL_URL_REFILL,
                new OperationRefillDtoRq(BigDecimal.valueOf(1000), accountNumberRub,
                        "Пополнение", RUB_CURRENCY),
                Void.class);
        assertEquals(
                BigDecimal.valueOf(1000).setScale(2, RoundingMode.CEILING),
                accountRepository.findAccountByNumber(accountNumberRub).getBalance());
        RestAssured
                .given()
                .body(new OperationBuyCurrencyDto(accountNumberRub,
                        accountNumberDollar, BigDecimal.valueOf(100), token))
                .contentType(ContentType.JSON)
                .log().all()
                .post(BASE_URL + "/buy")
                .then()
                .statusCode(201);
        assertEquals(
                BigDecimal.valueOf(900).setScale(2, RoundingMode.CEILING),
                accountRepository.findAccountByNumber(accountNumberRub).getBalance()
        );
        assertEquals(
                BigDecimal.ONE.setScale(2, RoundingMode.CEILING),
                accountRepository.findAccountByNumber(accountNumberDollar).getBalance()
        );

    }
}