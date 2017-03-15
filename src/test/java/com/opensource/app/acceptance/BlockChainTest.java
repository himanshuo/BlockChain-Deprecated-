package com.opensource.app;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlockChainTest {

  @Before
  public void before() {
    Internet.reset();
  }


  @Test
  public void testBlockChain() {
    Client a = new Client();  // miner
    Client b = new Client();
    Client c = new Client();

    Internet.mine(a);

    assertEquals("A has invalid number of coins", 5, a.coins);
    assertEquals("B has invalid number of coins", 0, b.coins);
    assertEquals("C has invalid number of coins", 0, c.coins);

    assertTrue(a.send(3,b));
    assertTrue(a.send(2,c));
    assertTrue(c.send(1,b));
    assertTrue(b.send(3,a));

    Internet.mine(b);

    assertEquals("A has invalid number of coins post sending", 3, a.coins);
    assertEquals("B has invalid number of coins post sending", 8, b.coins);
    assertEquals("C has invalid number of coins post sending", 1, c.coins);

    assertEquals(a.ledger, b.ledger);
    assertEquals(a.ledger, c.ledger);
    assertEquals(b.ledger, c.ledger);
  }



    // @Test
    // public void testSendSingle(){
    //     Client a = new Client();
    //     Client b = new Client();
    //
    //     assertEquals(6, a.coins);
    //     assertEquals(5, b.coins);
    //
    //     assertTrue(a.send(3, b));
    //
    //     assertEquals(3, a.coins);
    //     assertEquals(8, b.coins);
    //
    //     TestUtils.assertConsistentLedger(a.ledger, b.ledger);
    // }
    //
    // @Test
    // public void testSendDouble() {
    //     Client a = new Client(6);
    //     Client b = new Client(5);
    //
    //     assertEquals(6, a.coins);
    //     assertEquals(5, b.coins);
    //
    //     assertTrue(a.send(3, b));
    //
    //     assertEquals(3, a.coins);
    //     assertEquals(8, b.coins);
    //     TestUtils.assertConsistentLedger(a.ledger, b.ledger);
    //
    //     assertTrue(b.send(2, a));
    //
    //     assertEquals(5, a.coins);
    //     assertEquals(6, b.coins);
    //     TestUtils.assertConsistentLedger(a.ledger, b.ledger);
    // }
    //
    // @Test
    // public void testCannotSendWhatDoNotHave() {
    //     Client a = new Client(6);
    //     Client b = new Client(5);
    //
    //     assertEquals(6, a.coins);
    //     assertEquals(5, b.coins);
    //
    //     assertFalse(a.send(7, b));
    //
    //     assertEquals(6, a.coins);
    //     assertEquals(5, b.coins);
    //     TestUtils.assertConsistentLedger(a.ledger, b.ledger);
    //     assertEquals(2, a.ledger.size());
    // }
    //
    // @Test
    // public void testCannotLessThanZero() {
    //     Client a = new Client(6);
    //     Client b = new Client(5);
    //
    //     assertEquals(6, a.coins);
    //     assertEquals(5, b.coins);
    //
    //     assertFalse(a.send(-2, b));
    //
    //     assertEquals(6, a.coins);
    //     assertEquals(5, b.coins);
    //     TestUtils.assertConsistentLedger(a.ledger, b.ledger);
    //     assertEquals(2, a.ledger.size());
    // }
    //
    // @Test
    // public void testTransactionsQueingInternally() {
    //     Client a = new Client(3);
    //     Client b = new Client(0);
    //
    //     assertEquals(3, a.coins);
    //     assertEquals(0, b.coins);
    //
    //     assertTrue(a.send(1, b));
    //     assertEquals(2, a.coins);
    //     assertEquals(1, b.coins);
    //     TestUtils.assertConsistentLedger(a.ledger, b.ledger);
    //
    //     assertTrue(a.send(1, b));
    //     assertEquals(1, a.coins);
    //     assertEquals(2, b.coins);
    //     TestUtils.assertConsistentLedger(a.ledger, b.ledger);
    //
    //     assertTrue(a.send(1, b));
    //     assertEquals(0, a.coins);
    //     assertEquals(3, b.coins);
    //     TestUtils.assertConsistentLedger(a.ledger, b.ledger);
    //
    //     assertFalse(a.send(1, b));
    //     assertEquals(0, a.coins);
    //     assertEquals(3, b.coins);
    //     TestUtils.assertConsistentLedger(a.ledger, b.ledger);
    // }
    //
    // @Test
    // public void testSendAfterFail() {
    //     Client a = new Client(3);
    //     Client b = new Client(0);
    //
    //     assertEquals(3, a.coins);
    //     assertEquals(0, b.coins);
    //
    //     assertFalse(a.send(4, b));
    //     assertEquals(3, a.coins);
    //     assertEquals(0, b.coins);
    //     TestUtils.assertConsistentLedger(a.ledger, b.ledger);
    //
    //     assertTrue(a.send(1, b));
    //     assertEquals(2, a.coins);
    //     assertEquals(1, b.coins);
    //     TestUtils.assertConsistentLedger(a.ledger, b.ledger);
    //
    // }

}
