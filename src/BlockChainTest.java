import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by himanshu on 12/7/16.
 */
public class BlockChainTest {

    @Before
    public void before(){

    }

    @After
    public void after(){
        Internet.reset();
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