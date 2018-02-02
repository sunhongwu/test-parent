package com.shw.test.account.captcha.util;

import java.util.Random;

/**
 * Created by sunhongwu on 2018/2/2.
 */
public class RandomGenerator {

    private static String range = "0123456789abcdefghijklmnopqrstuvwxyz";

    public static synchronized String getRandowString(){
        Random random = new Random();
        StringBuffer result = new StringBuffer();
        for (int i = 0; i<8; i++){
            result.append(range.charAt(random.nextInt(range.length())));
        }
        return result.toString();
    }
}
