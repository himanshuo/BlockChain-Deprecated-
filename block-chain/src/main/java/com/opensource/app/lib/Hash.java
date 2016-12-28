
package lib;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by himanshu on 12/8/16.
 */
public class Hash {

    public static class UnknownByteConversionException extends Exception{}

    public static  byte [] hash(Object o) throws NoSuchAlgorithmException, IOException,UnknownByteConversionException {
        //note: SHA-256 is 256 bits (32 bytes) long.
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        if(o instanceof Hashable){
            md.update(((Hashable)o).getBytes());
        } else if (o instanceof String){
            md.update(((String)o).getBytes());
        } else {
            throw new UnknownByteConversionException();
        }
        return md.digest();
    }


    public static String toHexString(byte[] b){
        char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[b.length * 2];
        for ( int j = 0; j < b.length; j++ ) {
            int v = b[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


    public static  byte [] doubleHash(Object o) throws NoSuchAlgorithmException, IOException, UnknownByteConversionException{
        byte [] single = Hash.hash(o);
        return Hash.hash(toHexString(single));
    }
}
