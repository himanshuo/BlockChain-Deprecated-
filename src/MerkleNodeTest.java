import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by himanshu on 12/8/16.
 */
public class MerkleNodeTest {

    @Test
    public void testRootObjectSet() throws Exception {
        MerkleTree mt = new MerkleTree("merkle is cooler than urkle");
        MerkleTree.MerkleNode mn = mt.root;
        assertEquals(32, mt.root.value.length);
    }

}