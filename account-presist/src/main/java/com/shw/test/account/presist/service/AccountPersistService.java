package com.shw.test.account.presist.service;

import com.shw.test.account.presist.exception.AccountPersistException;
import com.shw.test.account.presist.model.Account;

public interface AccountPersistService {

    Account createAccount(Account account) throws AccountPersistException;

    Account readAccount(String id) throws AccountPersistException;

    Account updateAccount(Account account) throws AccountPersistException;

    void deleteAccount(String id) throws AccountPersistException;

}
