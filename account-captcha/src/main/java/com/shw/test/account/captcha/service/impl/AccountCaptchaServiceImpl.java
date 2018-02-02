package com.shw.test.account.captcha.service.impl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.shw.test.account.captcha.exception.AccountCaptchaException;
import com.shw.test.account.captcha.service.AccountCaptchaService;
import com.shw.test.account.captcha.util.RandomGenerator;
import org.springframework.beans.factory.InitializingBean;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by sunhongwu on 2018/2/2.
 */
public class AccountCaptchaServiceImpl implements AccountCaptchaService  ,InitializingBean{

    private DefaultKaptcha producer;

    private Map<String,String> captchaMap = new HashMap<String, String>();

    private List<String> preDefinedTexts;

    private int textCount = 0;

    public String generateCaptchaKey() throws AccountCaptchaException {
        String key = RandomGenerator.getRandowString();
        String value = getCaptchaText();
        captchaMap.put(key,value);
        return key;
    }

    public byte[] generateCaptchaImage(String captchKey) throws AccountCaptchaException {
        String text = captchaMap.get(captchKey);
        if(text == null){
            throw new AccountCaptchaException("CatchKey'"+captchKey+"'not found!");
        }
        BufferedImage image = producer.createImage(text);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(image,"jpg",out);
        }catch (IOException e){
            throw new AccountCaptchaException("Failed to write captcha stream",e);
        }
        return out.toByteArray();
    }

    public boolean validateCaptcha(String captchaKey, String captchaValue) throws AccountCaptchaException {
        String text = captchaMap.get(captchaKey);

        if(text == null){
            throw new AccountCaptchaException("CatchKey'"+captchaKey+"'not found!");
        }

        if(text == captchaValue){
            captchaMap.remove(captchaKey);
            return true;
        }else{
            return false;
        }
    }

    public List<String> getPreDefinedTexts() {
        return preDefinedTexts;
    }

    public void setPreDefinedTexts(List<String> preDefinedTexts) {
        this.preDefinedTexts = preDefinedTexts;
    }

    public void afterPropertiesSet() throws Exception {

        producer = new DefaultKaptcha();

        producer.setConfig(new Config(new Properties()));
    }

    private String getCaptchaText(){
        if(preDefinedTexts != null && !preDefinedTexts.isEmpty()){
            String text = preDefinedTexts.get(textCount);
            textCount = (textCount+1)%preDefinedTexts.size();
            return text;
        }else{
            return producer.createText();
        }
    }
}
