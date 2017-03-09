package com.opensource.app;

import static org.junit.Assert.*;
import java.util.ArrayList;

// todo (himanshuo): evidence piling up to make ledger a class

public class TestUtils {

  static void assertLedgerContainsTransaction(ArrayList<Transaction> ledger, Transaction t){
    for(int i =0; i < ledger.size(); i++){
      if(equalHash(ledger.get(i).hash, t.hash)) return;
    }
    fail();
  }

  static void assertConsistentLedger(ArrayList<Transaction> ledgerOne, ArrayList<Transaction> ledgerTwo){
    if(ledgerOne==null ^ ledgerTwo==null) fail();
    assertEquals(ledgerOne.size(), ledgerTwo.size());
    // all one contained in two
    for(Transaction t: ledgerOne) {
      if(!ledgerTwo.contains(t)) fail();
    }
    // all two container in one
    for(Transaction t: ledgerTwo) {
      if(!ledgerOne.contains(t)) fail();
    }
  }

  static boolean equalHash(byte[] one, byte[] two) {
    if((one == null) ^ (two == null)) return false;
    if(one == null && two == null) return true;
    if(one.length != two.length) return false;
    for(int i =0; i< two.length; i++) {
      if(one[i] != two[i]) return false;
    }
    return true;
  }
  static void assertEqualHash(byte[] expected, byte[] actual) {
    assertTrue(equalHash(expected, actual));
  }

}
