package org.javaacademy.online_bank.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import org.javaacademy.online_bank.dto.OperationPayDtoRq;
import org.javaacademy.online_bank.dto.OperationRefillDtoRq;
import org.javaacademy.online_bank.entity.Currency;
import org.javaacademy.online_bank.entity.Operation;
import org.javaacademy.online_bank.repository.OperationRepository;
import org.javaacademy.online_bank.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.TreeSet;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RequiredArgsConstructor
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OperationControllerTest extends AbstractIntegrationTest {
    private static final String DESCRIPTION_REFILL = "Пополнение!";
    private static final String DESCRIPTION_PAY = "Платеж!";
    private static final String BASE_URL = "/operation";
    private static final String FULL_URL = "http://localhost:8080/operation/receive";
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RestTemplate restTemplate;

    @Test
    void refillSuccess() {
        String token = getToken();
        String accountNumber = createAccount(token);
        RestAssured
                .given()
                .body(new OperationRefillDtoRq(BigDecimal.TEN, accountNumber, DESCRIPTION_REFILL, "Рубль"))
                .contentType(ContentType.JSON)
                .log().all()
                .post(BASE_URL + "/receive")
                .then()
                .statusCode(201);
        TreeSet<Operation> operations = operationRepository.getAllOperationsByNumberAccount(accountNumber);
        assertEquals(1, operations.size());
        Operation operation = operations.stream()
                .findFirst()
                .orElseThrow();
        assertEquals(accountNumber, operation.getNumberAccount());
        assertEquals(DESCRIPTION_REFILL, operation.getDescription());
        assertEquals(BigDecimal.TEN, operation.getAmount());
    }

    @Test
    void paySuccess() {
        String token = getToken();
        String accountNumber = createAccount(token);
        refill(BigDecimal.valueOf(30), accountNumber);
        pay(BigDecimal.valueOf(30), 201, token, accountNumber);
        assertEquals(
                BigDecimal.ZERO.setScale(2, RoundingMode.CEILING),
                accountService.getBalance(accountNumber));
        TreeSet<Operation> operations = operationRepository.getAllOperationsByNumberAccount(accountNumber);
        assertEquals(2, operations.size());
        Operation operation = operations.stream()
                .findFirst()
                .orElseThrow();
        assertEquals(accountNumber, operation.getNumberAccount());
        assertEquals(DESCRIPTION_PAY, operation.getDescription());
        assertEquals(BigDecimal.valueOf(30), operation.getAmount());
    }

    @Test
    void payFail() {
        String token = getToken();
        String accountNumber = createAccount(token);
        pay(BigDecimal.valueOf(51), 503, token, accountNumber);
    }

    public void pay(BigDecimal amount, Integer code, String token, String accountNumber) {
        RestAssured
                .given()
                .body(new OperationPayDtoRq(token, amount, accountNumber, DESCRIPTION_PAY, "Рубль"))
                .contentType(ContentType.JSON)
                .log().all()
                .post(BASE_URL + "/pay")
                .then()
                .statusCode(code);
    }

    public void refill(BigDecimal amount, String accountNumber) {
        OperationRefillDtoRq reqBody = new OperationRefillDtoRq(amount, accountNumber, DESCRIPTION_REFILL, "Рубль");
        restTemplate.postForEntity(
                FULL_URL,
                reqBody,
                Void.class);
    }

}