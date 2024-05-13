package org.javaacademy.online_bank.controller;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import org.javaacademy.online_bank.dto.AccountDto;
import org.javaacademy.online_bank.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AccountControllerTest extends AbstractIntegrationTest {
    private static final String BASE_URL = "/account";
    private static final String FAIL_TOKEN = "FAIL";
    @Autowired
    private AccountService accountService;

    @Test
    void createAccountSuccess() {
        String accountNumber = createAccount(getToken(), RUB_CURRENCY);
        assertEquals("810000001", accountNumber);
        assertEquals(
                BigDecimal.ZERO.setScale(2 , RoundingMode.CEILING),
                accountService.getBalance(accountNumber));
    }

    @Test
    void getBalanceSuccess() {
        String token = getToken();
        String accountNumber = createAccount(token, RUB_CURRENCY);
        BigDecimal bigDecimal = RestAssured
                .get(BASE_URL + "/balance?token=" + token + "&accountNumber=" + accountNumber)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body()
                .as(BigDecimal.class);
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.CEILING), bigDecimal);
    }

    @Test
    void getBalanceFail() {
        String accountNumber = createAccount(getToken(), RUB_CURRENCY);
        RestAssured
                .get(BASE_URL + "/balance?token=" + FAIL_TOKEN + "&number=" + accountNumber)
                .then()
                .log().all()
                .statusCode(503);
    }

    @Test
    void getListAccountByTokenSuccess() {
        String token = getToken();
        createAccount(token, RUB_CURRENCY);
        createAccount(token, RUB_CURRENCY);
        TypeRef<List<AccountDto>> typeRef = new TypeRef<>() {
        };
        List<AccountDto> accounts = RestAssured
                .get(BASE_URL + "?token=" + token)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body()
                .as(typeRef);
        assertEquals(2, accounts.size());
    }

    @Test
    void getListAccountByTokenFail() {
        String token = getToken();
        createAccount(token, RUB_CURRENCY);
        createAccount(token, RUB_CURRENCY);
        TypeRef<List<AccountDto>> typeRef = new TypeRef<>() {
        };
        RestAssured
                .get(BASE_URL + "?token=" + FAIL_TOKEN)
                .then()
                .log().all()
                .statusCode(503);
    }


}