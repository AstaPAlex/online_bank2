package org.javaacademy.online_bank.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.javaacademy.online_bank.dto.UserAuthDto;
import org.javaacademy.online_bank.dto.UserDto;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractIntegrationTest {
    protected static final String FULL_NAME = "Иванов Иван Иваныч";
    protected static final String PHONE_NUMBER = "+7-111-111-11-11";
    protected static final String PREFIX = "online";
    protected static final String POSTFIX = "token";

    public String signup() {
        return RestAssured
                .given()
                .body(new UserDto(FULL_NAME, PHONE_NUMBER))
                .contentType(ContentType.JSON)
                .log().all()
                .post("/user/signup")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .body()
                .asString();
    }

    public String auth(String pinCode) {
        return RestAssured
                .given()
                .body(new UserAuthDto(PHONE_NUMBER, pinCode))
                .contentType(ContentType.JSON)
                .log().all()
                .post("/user/auth")
                .then()
                .log().all()
                .statusCode(202)
                .extract()
                .body()
                .asString();
    }

    public String createAccount(String token) {
        Map<String, String> mapToken = new HashMap<>();
        mapToken.put("token", token);
        return RestAssured
                .given()
                .body(mapToken)
                .contentType(ContentType.JSON)
                .post("/account")
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();
    }

    public String getToken() {
        String pinCode = signup();
        return auth(pinCode);
    }

}
