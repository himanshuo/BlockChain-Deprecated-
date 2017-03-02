package com.opensource.app;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlockChainTest {

  @Before
  public void before(){

  }

  @After
  public void after(){
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

    a.send(3,b);

    a.send(2,b);

    c.send(1,b);

    b.send(3,a);

    assertEquals("A has invalid number of coins post sending", 4, a.coins);
    assertEquals("B has invalid number of coins post sending", 8, b.coins);
    assertEquals("C has invalid number of coins post sending", 0, c.coins);


    //each knows about the transaction history
    assertEquals("A.ledger != B.ledger", a.ledger.toString(), b.ledger.toString());
    assertEquals("A.ledger != C.ledger", a.ledger.toString(), c.ledger.toString());
    assertEquals("B.ledger != C.ledger", b.ledger.toString(), c.ledger.toString());
  }



    @Test
    public void testSendSingle(){
        Client a = new Client(6);
        Client b = new Client(5);

        assertEquals(6, a.coins);
        assertEquals(5, b.coins);

        assertTrue(a.send(b, 3));

        assertEquals(3, a.coins);
        assertEquals(8, b.coins);

        assertEquals(1, a.ledger.size());
        assertEquals(a.ledger.get(0).hash, b.ledger.get(0).hash);
        assertEquals(a.ledger.get(0).amount, b.ledger.get(0).amount);
        assertEquals(3, a.ledger.get(0).amount);
    }

    @Test
    public void testSendDouble(){
        Client a = new Client(6);
        Client b = new Client(5);

        assertEquals(6, a.coins);
        assertEquals(5, b.coins);

        assertTrue(a.send(b, 3));

        assertEquals(3, a.coins);
        assertEquals(8, b.coins);

        assertEquals(1, a.ledger.size());
        assertEquals(a.ledger.get(0).hash, b.ledger.get(0).hash);
        assertEquals(a.ledger.get(0).amount, b.ledger.get(0).amount);
        assertEquals(3, a.ledger.get(0).amount);

        assertTrue(b.send(a, 2));

        assertEquals(5, a.coins);
        assertEquals(6, b.coins);

        assertEquals(2, a.ledger.size());

        assertEquals(a.ledger.get(0).hash, b.ledger.get(0).hash);
        assertEquals(a.ledger.get(0).amount, b.ledger.get(0).amount);
        assertEquals(a.ledger.get(1).hash, b.ledger.get(1).hash);
        assertEquals(a.ledger.get(1).amount, b.ledger.get(1).amount);

        assertEquals(3, a.ledger.get(0).amount);
        assertEquals(2, a.ledger.get(1).amount);
    }

}
