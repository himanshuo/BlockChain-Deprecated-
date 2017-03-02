package com.opensource.app.lib;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by himanshu on 12/10/16.
 */
public class HashTest {
    @Test
    public void hash() throws Exception {

    }

    @Test
    public void toHexString() throws Exception {
        String testCase = "Hello, world!0";
        String expected = "1312af178c253f84028d480a6adc1e25e81caa44c749ec81976192e2ec934c64";
        String actual = Hash.toHexString(Hash.hash(testCase));
        assertEquals(expected, actual);
    }

    @Test
    public void doubleHash() throws Exception {
        String testCase = "Hello, world!0";
        String expected = "c90b111659c9b88f5966e4966e03480721af9175f1636172935011fbcf3a9f57";
        String actual = Hash.toHexString(Hash.doubleHash(testCase));
        assertEquals(expected, actual);
    }

}
