// package com.opensource.app;
//
// import org.junit.Before;
// import org.junit.Test;
// import java.util.ArrayList;
// import static org.junit.Assert.*;
//
// public class TransactionTest {
//
//   @Before
//   public void before(){
//       Internet.reset();
//   }
//
//
//   // @Test
//   // public void testVerifyDiffPointersToSameTransaction(){
//   //   try {
//   //     Transaction a = new Transaction(new ArrayList<InputTransaction>(), new ArrayList<OutputTransaction>());
//   //     Transaction aa = a;
//   //     Transaction aaa = a;
//   //
//   //     assertEqualHash(a.hash, aa.hash);
//   //     assertEqualHash(aa.hash, aaa.hash);
//   //     assertEqualHash(a.hash, aaa.hash);
//   //   } catch (Exception e) {
//   //     e.printStackTrace();
//   //     fail();
//   //   }
//   // }
//
//   // todo (himanshuo): test client send method in a client test class
//
//   @Test
//   public void testTransactionInTwoLedgersHaveSameHash(){
//     Client a = new Client(6);
//     Client b = new Client(3);
//     assertTrue(a.send(3, b));
//     TestUtils.assertConsistentLedger(a.ledger, b.ledger);
//     assertEquals(3, a.coins);
//     assertEquals(6, b.coins);
//   }
//
//   @Test
//   public void testTransactionInTwoLedgersHaveSameHashReverse(){
//     Client a = new Client(6);
//     Client b = new Client(3);
//     assertTrue(b.send(3, a));
//     TestUtils.assertConsistentLedger(a.ledger, b.ledger);
//     assertEquals(9, a.coins);
//     assertEquals(0, b.coins);
//   }
//
// }
