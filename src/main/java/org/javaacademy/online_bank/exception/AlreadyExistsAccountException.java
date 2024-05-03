package org.javaacademy.online_bank.exception;

import lombok.experimental.StandardException;

@StandardException
public class AlreadyExistsAccountException extends RuntimeException {
    public AlreadyExistsAccountException() {
        super("Такой счет уже существует!");
    }
}
