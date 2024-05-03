package org.javaacademy.online_bank.exception;

import lombok.experimental.StandardException;

@StandardException
public class LimitCountAccountException extends RuntimeException {
    public LimitCountAccountException() {
        super("Превышено кол-во счетов!");
    }
}
