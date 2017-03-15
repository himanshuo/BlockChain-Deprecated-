package com.opensource.app;

//todo (himanshuo): perhaps create a new package for Hash
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.lang.CloneNotSupportedException;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Created by himanshu on 12/8/16.
 */
public class Hash {

    public byte[] hash; // 32 bytes
    //SHA-256 digest is 256 bits (32 bytes) long
    private final String HASHING_ALGORITHM = "SHA-256";
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    public Hash(String bytes) throws NoSuchAlgorithmException, IOException {
      MessageDigest md = MessageDigest.getInstance(HASHING_ALGORITHM);
      md.update(bytes.getBytes());
      this.hash = md.digest();
      assert this.hash.length == 32;
      LOGGER.log(Level.INFO, "Generated hash from String " + bytes + " -> " + this.toString());
    }

    public String toString() {
        char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[this.hash.length * 2];
        for ( int j = 0; j < this.hash.length; j++ ) {
            int v = this.hash[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public boolean equals(Object other) {
      if(this == other) return true;
      if(!(other instanceof Hash)) return false;
      Hash otherHash = (Hash)other;
      if(this.hash.length != otherHash.hash.length) return false;
      for(int i =0; i< this.hash.length; i++) {
        if(this.hash[i] != otherHash.hash[i]) return false;
      }
      return true;
    }

    public Hash rehash() throws NoSuchAlgorithmException, IOException {
      LOGGER.log(Level.INFO, "rehashing "+ this.toString());
        return new Hash(this.toString());
    }

    public byte[] getBytes() {
      LOGGER.log(Level.INFO, "getBytes:"+this.toString());
      return this.hash;
    }

    protected Hash clone() throws CloneNotSupportedException {
      try {
          LOGGER.log(Level.INFO, "Cloning Hash:" + this.toString());
          return new Hash(this.toString());
      } catch (Exception e) {
          LOGGER.log(Level.SEVERE, e.toString(), e.fillInStackTrace());
          throw new CloneNotSupportedException();
      }

    }
}
