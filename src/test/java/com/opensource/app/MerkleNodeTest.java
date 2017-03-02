package com.opensource.app;

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
        assertEquals(
            "9ad9cb0110ab8bf791ad990f76c7007d12ed9692a751e7ceb2e7f0e8391435cb",
            mt.root.toString()
        );
    }

}
