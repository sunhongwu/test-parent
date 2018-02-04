package com.shw.test.account.service.service;

import com.shw.test.account.service.exception.AccountServiceException;
import com.shw.test.account.service.model.SignUpRequest;

public interface AccountService {

    String generateCaptchaKey() throws AccountServiceException;

    byte[] generateCaptchaImage(String captchaKey) throws AccountServiceException;

    void signUp(SignUpRequest signUpRequest) throws AccountServiceException;

    void activate(String activationNumber) throws AccountServiceException;

    void login(String id, String password) throws AccountServiceException;
}
