package com.shw.test.account.captcha.service;

import com.shw.test.account.captcha.exception.AccountCaptchaException;

import java.util.List;

/**
 * Created by sunhongwu on 2018/2/2.
 */
public interface AccountCaptchaService {

    /**
     * 生成验证码key
     * @return
     * @throws AccountCaptchaException
     */
    String generateCaptchaKey() throws AccountCaptchaException;

    /**
     * 生成验证码图片
     * @param captchKey
     * @return
     * @throws AccountCaptchaException
     */
    byte[] generateCaptchaImage(String captchKey) throws AccountCaptchaException;

    /**
     * 验证客户回填的key和验证码值
     * @param captchaKey
     * @param captchaValue
     * @return
     * @throws AccountCaptchaException
     */
    boolean validateCaptcha(String captchaKey, String captchaValue) throws AccountCaptchaException;

    /**
     * 获取验证码图片的内容
     * @return
     */
    List<String> getPreDefinedTexts();

    /**
     * 预定义验证码图片的内容
     * @param preDefinedTexts
     */
    void setPreDefinedTexts(List<String> preDefinedTexts);
}
