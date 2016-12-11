package lib;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by himanshu on 12/10/16.
 */
public class ProofOfWork {

    final static int MAX_NONCE = Integer.MAX_VALUE;

    //todo (himanshuo): byte arg is pass by value thus slow. put this inside SHA256 function
    // difficulty = number of bytes
    private static boolean valid(byte[] cur, int difficulty){
        assert cur.length == 32;
        for(int i = 0; i < difficulty; i++) {
            if (0x0 != cur[i]) return false;
        }
        return true;
    }

    public static class ExhaustedSearchSpace extends Exception {}

    public static byte [] SHA256(String in, int difficulty) throws NoSuchAlgorithmException, IOException, ExhaustedSearchSpace, Hash.UnknownByteConversionException {
        //todo (himanshuo): there needs to be some async thread to stop searching
        // if some other client already found the answer
        byte [] cur;

        // difficulty uses hex bits. Hash.hash uses byte array
        // difficulty = 1 represents 16 bits
        // Each element in Hash.hash is 8 bits.

        // Hash.hash has total of 32 bytes -> 256 bits, thus max difficulty can be 256/16 = 16
        //


        for(int nonce = 4250; nonce < MAX_NONCE ; nonce++){
            cur = Hash.hash(in + nonce);
            if(valid(cur, difficulty)) return cur;
        }
        throw new ExhaustedSearchSpace();
    }

}
