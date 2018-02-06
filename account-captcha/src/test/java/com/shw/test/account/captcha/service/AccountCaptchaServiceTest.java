package com.shw.test.account.captcha.service;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunhongwu on 2018/2/2.
 */
public class AccountCaptchaServiceTest {

    private AccountCaptchaService service;

    @BeforeMethod
    public void prepare()throws Exception{
        ApplicationContext ctx = new ClassPathXmlApplicationContext("account-captcha.xml");
        service = (AccountCaptchaService)ctx.getBean("accountCaptchaService");
    }

    @Test
    public void testGenerateCaptcha() throws Exception {
        String captchaKey = service.generateCaptchaKey();
        assertNotNull(captchaKey);

        byte[] captchaImage = service.generateCaptchaImage(captchaKey);
        assertTrue(captchaImage.length > 0);
        File image = new File("target/"+captchaKey+".jpg");
        OutputStream out = null;
        try {
            out = new FileOutputStream(image);
            out.write(captchaImage);
        }finally{
            if(out != null){
                out.close();
            }
        }
        assertTrue(image.exists() && image.length() > 0);
    }


    @Test
    public void testValidateCaptchaCorrect() throws Exception {
        List<String> preDefinedTexts = new ArrayList<String>();
        preDefinedTexts.add("12345");
        preDefinedTexts.add("abcde");
        service.setPreDefinedTexts(preDefinedTexts);

        String captchsKey = service.generateCaptchaKey();
        service.generateCaptchaImage(captchsKey);
        assertTrue(service.validateCaptcha(captchsKey,"12345"));

        captchsKey = service.generateCaptchaKey();
        service.generateCaptchaImage(captchsKey);
        assertTrue(service.validateCaptcha(captchsKey,"abcde"));

    }

    @Test
    public void testValidateCaptchaIncorrect() throws Exception {
        List<String> preDefinedTexts = new ArrayList<String>();
        preDefinedTexts.add("12345");
        service.setPreDefinedTexts(preDefinedTexts);

        String captchsKey = service.generateCaptchaKey();
        service.generateCaptchaImage(captchsKey);
        assertTrue(service.validateCaptcha(captchsKey,"12345"));

    }

}