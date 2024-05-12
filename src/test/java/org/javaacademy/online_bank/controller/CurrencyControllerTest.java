package org.javaacademy.online_bank.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.javaacademy.online_bank.dto.ExchangeRateDto;
import org.javaacademy.online_bank.entity.Currency;
import org.javaacademy.online_bank.entity.ExchangeRate;
import org.javaacademy.online_bank.repository.CurrencyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CurrencyControllerTest extends AbstractIntegrationTest {
    private static final String BASE_URL = "/currency";
    @Autowired
    private CurrencyRepository currencyRepository;

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
        Assertions.assertEquals(BigDecimal.valueOf(100), exchangeRates.getRate());
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
}