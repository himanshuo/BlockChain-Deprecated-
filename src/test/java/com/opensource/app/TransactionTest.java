package com.opensource.app;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class TransactionTest {

  @Before
  public void before(){
      Internet.reset();
  }

  void assertEqualHash(byte[] expected, byte[] actual) {
    if(expected == null ^ actual == null) fail();
    assertEquals(expected.length, actual.length);
    for(int i =0; i< expected.length; i++) {
      assertEquals(expected[i], actual[i]);
    }
  }

  // @Test
  // public void testVerifyDiffPointersToSameTransaction(){
  //   try {
  //     Transaction a = new Transaction(new ArrayList<InputTransaction>(), new ArrayList<OutputTransaction>());
  //     Transaction aa = a;
  //     Transaction aaa = a;
  //
  //     assertEqualHash(a.hash, aa.hash);
  //     assertEqualHash(aa.hash, aaa.hash);
  //     assertEqualHash(a.hash, aaa.hash);
  //   } catch (Exception e) {
  //     e.printStackTrace();
  //     fail();
  //   }
  // }


  @Test
  public void testTransactionInTwoLedgersHaveSameHash(){
    Client a = new Client(6);
    Client b = new Client(3);
    a.send(3, b);
    assertConsistentLedger(a.ledger, b.ledger);
  }

  // @Test
  // public void testTransactionInTwoLedgersHaveSameHashReverse(){
  //   Client a = new Client(6);
  //   Client b = new Client(3);
  //   b.send(3, a);
  //   assertEqualHash(a.ledger.get(0).hash, b.ledger.get(0).hash);
  // }

}
