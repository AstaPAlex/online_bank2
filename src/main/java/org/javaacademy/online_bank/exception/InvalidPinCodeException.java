package org.javaacademy.online_bank.exception;

import lombok.experimental.StandardException;

@StandardException
public class InvalidPinCodeException extends RuntimeException {
    public InvalidPinCodeException() {
        super("Не совпадает номер или пин код пользователя!");
    }
}
