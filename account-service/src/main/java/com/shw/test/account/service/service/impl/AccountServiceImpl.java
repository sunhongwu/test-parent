package com.shw.test.account.service.service.impl;

import com.shw.test.account.captcha.exception.AccountCaptchaException;
import com.shw.test.account.captcha.service.AccountCaptchaService;
import com.shw.test.account.captcha.util.RandomGenerator;
import com.shw.test.account.email.exception.AccountEmailException;
import com.shw.test.account.email.service.AccountEmailService;
import com.shw.test.account.presist.exception.AccountPersistException;
import com.shw.test.account.presist.model.Account;
import com.shw.test.account.presist.service.AccountPersistService;
import com.shw.test.account.service.exception.AccountServiceException;
import com.shw.test.account.service.model.SignUpRequest;
import com.shw.test.account.service.service.AccountService;

import java.util.HashMap;
import java.util.Map;


public class AccountServiceImpl implements AccountService{

    private AccountPersistService accountPersistService;

    private AccountCaptchaService accountCaptchaService;

    private AccountEmailService accountEmailService;

    private Map<String,String> activationMap = new HashMap<String,String>();

    @Override
    public String generateCaptchaKey() throws AccountServiceException {
        try {
            return accountCaptchaService.generateCaptchaKey();
        }catch (AccountCaptchaException e){
            throw new AccountServiceException("Unable to generate Captcha key.",e);
        }
    }

    @Override
    public byte[] generateCaptchaImage(String captchaKey) throws AccountServiceException {
        try {
            return accountCaptchaService.generateCaptchaImage(captchaKey);
        }catch (AccountCaptchaException e){
            throw new AccountServiceException("Unable to generate Captcha Image.",e);
        }
    }

    @Override
    public void signUp(SignUpRequest signUpRequest) throws AccountServiceException {
        try {
            if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())){
                throw new AccountServiceException("2 password do not match.");
            }

            if (!accountCaptchaService.validateCaptcha(signUpRequest.getCaptchaKey(),signUpRequest.getCaptchaValue())){
                throw  new AccountServiceException("Incorrect Captcha.");
            }

            Account account = new Account();
            account.setId(signUpRequest.getId());
            account.setName(signUpRequest.getName());
            account.setPassword(signUpRequest.getPassword());
            account.setEmail(signUpRequest.getEmail());
            account.setActivated(false);

            accountPersistService.createAccount(account);

            String activationId = RandomGenerator.getRandowString();
            activationMap.put(activationId,account.getId());
            String link = signUpRequest.getActivateServiceUrl().endsWith("/")?signUpRequest.getActivateServiceUrl()
                    +activationId:signUpRequest.getActivateServiceUrl()+"?key="+activationId;

            accountEmailService.send(account.getEmail(),"Please Activate Your Account",link);


        }catch (AccountCaptchaException e){
            throw new AccountServiceException("Unable to validate captcha.",e);
        }catch (AccountPersistException e){
            throw new AccountServiceException("Unable to create account.",e);
        }catch (AccountEmailException e){
            throw new AccountServiceException("Unable to send actiavtion mail.",e);
        }
    }

    @Override
    public void activate(String activationNumber) throws AccountServiceException {
        String accountId = activationMap.get(activationNumber);

        if (accountId == null){
            throw new AccountServiceException("Invalid Account activation ID.");
        }

        try {
            Account account = accountPersistService.readAccount(accountId);
            account.setActivated(true);
            accountPersistService.updateAccount(account);
        }catch (AccountPersistException e){
            throw new AccountServiceException("Unable to activate account.");
        }
    }

    @Override
    public void login(String id, String password) throws AccountServiceException {
        try {
            Account account = accountPersistService.readAccount(id);

            if(account == null){
                throw new AccountServiceException("Account does not exist.");
            }

            if(!account.isActivated()){
                throw new AccountServiceException("Account is disabled.");
            }

            if(!account.getPassword().equals(password)){
                throw new AccountServiceException("Incorrect password.");
            }
        }catch (AccountPersistException e){
            throw new AccountServiceException("Unable to log in.",e);
        }
    }

    public AccountPersistService getAccountPersistService() {
        return accountPersistService;
    }

    public void setAccountPersistService(AccountPersistService accountPersistService) {
        this.accountPersistService = accountPersistService;
    }

    public AccountCaptchaService getAccountCaptchaService() {
        return accountCaptchaService;
    }

    public void setAccountCaptchaService(AccountCaptchaService accountCaptchaService) {
        this.accountCaptchaService = accountCaptchaService;
    }

    public AccountEmailService getAccountEmailService() {
        return accountEmailService;
    }

    public void setAccountEmailService(AccountEmailService accountEmailService) {
        this.accountEmailService = accountEmailService;
    }
}
