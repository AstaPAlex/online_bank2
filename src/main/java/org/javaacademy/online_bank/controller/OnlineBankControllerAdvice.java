package org.javaacademy.online_bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OnlineBankControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleIntegrationException(Exception e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("На сайте наблюдаются проблемы, приходите позже");
    }

}
