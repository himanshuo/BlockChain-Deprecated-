package com.opensource.app.lib;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by himanshu on 12/10/16.
 */
public class ProofOfWorkTest {

    @Test
    public void SHA256Positive() throws Exception {
        String input = "Hello, world!";
        String expected = "0000c3af42fc31103f1fdc0151fa747ff87349a4714df7cc52ea464e12dcd4e9";
        String actual = Hash.toHexString(ProofOfWork.SHA256(input, 2));
        assertEquals(expected, actual);
    }
}
