package com.shw.test.account.presist.service;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import com.shw.test.account.email.service.AccountEmailService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.mail.Message;

import static org.testng.Assert.*;

/**
 * Created by sunhongwu on 2018/1/29.
 */
public class AccountEmailServiceTest {

    private GreenMail greenMail;

    @BeforeMethod
    public void setUp() throws Exception {
        greenMail = new GreenMail(ServerSetup.SMTP);
        greenMail.setUser("test@gmail.com","123456");
        greenMail.start();
    }

    @Test
    public void testSend() throws Exception {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("account-email.xml");
        AccountEmailService accountEmailService = (AccountEmailService)applicationContext.getBean("accountEmailService");
        String subject = "Test Subject";
        String htmlText = "<h3>Test</h3>";
        accountEmailService.send("test@gmail.com",subject,htmlText);
        greenMail.waitForIncomingEmail(2000,1);
        Message[] msgs = greenMail.getReceivedMessages();
        assertEquals(1,msgs.length);
        assertEquals(subject,msgs[0].getSubject());
        assertEquals(htmlText, GreenMailUtil.getBody(msgs[0]).trim());

    }


    @AfterMethod
    public void tearDown() throws Exception {
        greenMail.stop();
    }
}