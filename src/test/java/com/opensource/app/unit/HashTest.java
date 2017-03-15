package com.opensource.app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by himanshu on 12/10/16.
 */
public class HashTest {

    @Test
    public void hashString() throws Exception {
        String str = "Hello, world!0";
        Hash testCase = new Hash(str);
        String expected = "1312af178c253f84028d480a6adc1e25e81caa44c749ec81976192e2ec934c64";
        String actual = testCase.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void toStringReturnsHexVersionOfBytesInternalArray() throws Exception {
      Hash testCase = new Hash("");
      testCase.hash[0] = -128;
      testCase.hash[1] = 127;
      testCase.hash[2] = -100;
      testCase.hash[3] = 100;
      testCase.hash[4] = 1;
      testCase.hash[5] = -1;
      for(int i = 6; i < 32; i++) {
        testCase.hash[i] = 0;
      }
      String expected = "807f9c6401ff0000000000000000000000000000000000000000000000000000";
      String actual = testCase.toString();
      assertEquals(expected, actual);
    }

    @Test
    public void equalsSameObject() throws Exception {
      Hash testCase = new Hash("some temp string");
      assertTrue(testCase.equals(testCase));
    }

    @Test
    public void equalsSameString() throws Exception {
      String str = "some temp string";
      Hash a = new Hash(str);
      Hash b = new Hash(str);
      assertTrue(a.equals(b));
    }


    @Test
    public void rehash() throws Exception {
        String str = "Hello, world!0";
        String expected = "c90b111659c9b88f5966e4966e03480721af9175f1636172935011fbcf3a9f57";
        String actual = (new Hash(str)).rehash().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void getBytesReturnsInternalBytes() throws Exception {
      Hash testCase = new Hash("");
      byte[] expected = new byte[32];
      expected[0] = -128;
      expected[1] = 127;
      expected[2] = -100;
      expected[3] = 100;
      expected[4] = 1;
      expected[5] = -1;
      for(int i = 6; i < 32; i++) {
        expected[i] = 0;
      }
      testCase.hash = expected;
      byte[] actual = testCase.getBytes();
      assertEquals(expected.length, actual.length);
      assertEquals(32, actual.length);
      for(int i = 0; i < expected.length; i++) {
        assertEquals(expected[i], actual[i]);
      }
    }

    @Test
    public void cloneString() throws Exception {
      String str = "Hello, world!0";
      String expected = "c90b111659c9b88f5966e4966e03480721af9175f1636172935011fbcf3a9f57";
      String actual = (new Hash(str)).clone().toString();
      assertEquals(expected, actual);
    }

}
