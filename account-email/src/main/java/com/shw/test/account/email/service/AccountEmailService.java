package com.shw.test.account.email.service;

import com.shw.test.account.email.exception.AccountEmailException;

/**
 * Created by sunhongwu on 2018/1/29.
 */
public interface AccountEmailService {

    void send(String to, String subject, String htmlText) throws AccountEmailException;
}
