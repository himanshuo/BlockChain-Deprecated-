package com.opensource.app;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlockChainTest {

  @Before
  public void before(){
    Internet.reset();
  }


  @Test
  public void testBlockChain(){
    Client a = new Client(6);
    Client b = new Client(5);
    Client c = new Client(1);


    assertEquals("A has invalid number of coins", 6, a.coins);
    assertEquals("B has invalid number of coins", 5, b.coins);
    assertEquals("C has invalid number of coins", 1, c.coins);

    assertTrue(a.send(3,b));
    assertTrue(a.send(2,b));
    assertTrue(c.send(1,b));
    assertTrue(b.send(3,a));

    assertEquals("A has invalid number of coins post sending", 4, a.coins);
    assertEquals("B has invalid number of coins post sending", 8, b.coins);
    assertEquals("C has invalid number of coins post sending", 0, c.coins);


    //each knows about the transaction history
    TestUtils.assertConsistentLedger(a.ledger, b.ledger);
    TestUtils.assertConsistentLedger(b.ledger, c.ledger);
    TestUtils.assertConsistentLedger(a.ledger, c.ledger);
  }



    @Test
    public void testSendSingle(){
        Client a = new Client(6);
        Client b = new Client(5);

        assertEquals(6, a.coins);
        assertEquals(5, b.coins);

        assertTrue(a.send(3, b));

        assertEquals(3, a.coins);
        assertEquals(8, b.coins);

        TestUtils.assertConsistentLedger(a.ledger, b.ledger);
    }

    @Test
    public void testSendDouble() {
        Client a = new Client(6);
        Client b = new Client(5);

        assertEquals(6, a.coins);
        assertEquals(5, b.coins);

        assertTrue(a.send(3, b));

        assertEquals(3, a.coins);
        assertEquals(8, b.coins);
        TestUtils.assertConsistentLedger(a.ledger, b.ledger);

        assertTrue(b.send(2, a));

        assertEquals(5, a.coins);
        assertEquals(6, b.coins);
        TestUtils.assertConsistentLedger(a.ledger, b.ledger);
    }

    @Test
    public void testCannotSendWhatDoNotHave() {
        Client a = new Client(6);
        Client b = new Client(5);

        assertEquals(6, a.coins);
        assertEquals(5, b.coins);

        assertFalse(a.send(7, b));

        assertEquals(6, a.coins);
        assertEquals(5, b.coins);
        TestUtils.assertConsistentLedger(a.ledger, b.ledger);
        assertEquals(2, a.ledger.size());
    }

    @Test
    public void testCannotLessThanZero() {
        Client a = new Client(6);
        Client b = new Client(5);

        assertEquals(6, a.coins);
        assertEquals(5, b.coins);

        assertFalse(a.send(-2, b));

        assertEquals(6, a.coins);
        assertEquals(5, b.coins);
        TestUtils.assertConsistentLedger(a.ledger, b.ledger);
        assertEquals(2, a.ledger.size());
    }

}
