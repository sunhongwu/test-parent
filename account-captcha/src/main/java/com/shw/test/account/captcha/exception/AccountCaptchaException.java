package com.shw.test.account.captcha.exception;

/**
 * Created by sunhongwu on 2018/2/2.
 */
public class AccountCaptchaException extends RuntimeException {

    public AccountCaptchaException(String message){
        super(message);
    }

    public AccountCaptchaException(String message, Throwable cause) {
        super(message, cause);
    }
}
