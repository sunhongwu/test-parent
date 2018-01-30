package com.shw.test.account.presist.service;

import com.shw.test.account.presist.model.Account;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.*;

public class AccountPersistServiceTest {

    private AccountPersistService accountPersistService;

    @BeforeMethod
    public void prepare() throws Exception{
        File persistDataFile = new File("target/test-classes/persist-data.xml");
        if (persistDataFile.exists()){
            persistDataFile.delete();
        }
        ApplicationContext ctx = new ClassPathXmlApplicationContext("account-persist.xml");

        accountPersistService = (AccountPersistService)ctx.getBean("accountPersistService");

        Account account = new Account();
        account.setId("1");
        account.setName("test");
        account.setEmail("email");
        account.setPassword("password");
        account.setActivated(true);

        accountPersistService.createAccount(account);
    }
    @Test
    public void readAccount() throws Exception{

        Account account = accountPersistService.readAccount("1");

        assertNotNull(account);
        assertEquals("1",account.getId());
        assertEquals("test",account.getName());
        assertEquals("email",account.getEmail());
        assertEquals("password",account.getPassword());
        assertTrue(account.isActivated());
    }
}