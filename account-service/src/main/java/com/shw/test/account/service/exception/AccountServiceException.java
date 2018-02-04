package com.shw.test.account.service.exception;

public class AccountServiceException extends RuntimeException{

    public AccountServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountServiceException(String message) {
        super(message);
    }
}
