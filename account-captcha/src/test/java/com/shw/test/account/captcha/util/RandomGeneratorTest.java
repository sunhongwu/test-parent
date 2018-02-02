package com.shw.test.account.captcha.util;

import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.*;

/**
 * Created by sunhongwu on 2018/2/2.
 */
public class RandomGeneratorTest {
    @Test
    public void testGetRandowString() throws Exception {
        Set<String> randoms = new HashSet<String>(100);
        for (int i = 0; i <100; i++){
            String random = RandomGenerator.getRandowString();
            assertFalse(randoms.contains(random));
            randoms.add(random);
        }
    }

}