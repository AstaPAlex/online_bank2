package org.javaacademy.online_bank.controller;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import org.javaacademy.online_bank.dto.UserAuthDto;
import org.javaacademy.online_bank.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RequiredArgsConstructor
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserControllerTest extends AbstractIntegrationTest {
    private static final int COUNT_CHAR_IN_PIN_CODE = 4;
    private static final int COUNT_CHAR_IN_TOKEN = 47;
    private static final int END_PREFIX_TOKEN = 6;
    private static final int START_POSTFIX_TOKEN = 42;
    private static final String FAIL_PIN_CODE = "FAIL";
    private static final String BASE_URL = "/user";
    @Autowired
    private UserRepository userRepository;

    @Test
    void signupSuccess() {
        assertEquals(COUNT_CHAR_IN_PIN_CODE, signup().length());
        assertNotNull(userRepository.findUuidByNumberPhone(PHONE_NUMBER));
    }

    @Test
    void authSuccess() {
        String token = auth(signup());
        assertEquals(COUNT_CHAR_IN_TOKEN, token.length());
        assertEquals(PREFIX, token.substring(0, END_PREFIX_TOKEN));
        assertEquals(POSTFIX, token.substring(START_POSTFIX_TOKEN, COUNT_CHAR_IN_TOKEN));
    }

    @Test
    void authFail() {
        signup();
        RestAssured
                .given()
                .body(new UserAuthDto(PHONE_NUMBER, FAIL_PIN_CODE))
                .contentType(ContentType.JSON)
                .log().all()
                .post(BASE_URL + "/auth")
                .then()
                .log().all()
                .statusCode(503);
    }

}