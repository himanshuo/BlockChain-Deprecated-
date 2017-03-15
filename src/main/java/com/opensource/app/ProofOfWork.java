package com.opensource.app.lib;

import com.opensource.app.Hash;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by himanshu on 12/10/16.
 */
public class ProofOfWork {

    final static int MAX_NONCE = Integer.MAX_VALUE;

    //todo (himanshuo): byte arg is pass by value thus slow. put this inside SHA256 function
    // difficulty = number of bytes
    private static boolean valid(Hash cur, int difficulty){
        for(int i = 0; i < difficulty; i++) {
            if (0x0 != cur.hash[i]) return false;
        }
        return true;
    }

    public static class ExhaustedSearchSpace extends Exception {}

    public static Hash SHA256(String in, int difficulty) throws NoSuchAlgorithmException, IOException, ExhaustedSearchSpace {
        //todo (himanshuo): there needs to be some async thread to stop searching (vertx?)
        // if some other client already found the answer
        Hash cur;

        // difficulty uses hex bits. Hash uses byte array
        // difficulty = 1 represents 16 bits
        // Each element in Hash is 8 bits.

        // Hash has total of 32 bytes -> 256 bits, thus max difficulty can be 256/16 = 16


        for(int nonce = 4250; nonce < MAX_NONCE ; nonce++){
            cur = new Hash(in + nonce);
            if(valid(cur, difficulty)) return cur;
        }
        throw new ExhaustedSearchSpace();
    }

}
