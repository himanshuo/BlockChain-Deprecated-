package com.opensource.app;

import java.lang.CloneNotSupportedException;

/**
 * Created by himanshu on 12/14/16.
 */
public class InputTransaction {
    Hash hash;   //32 bit identifying hash of the input transaction
    int n;  // n=0 means first output from hash transaction
    String signature; // signature of person sending money
    String publicKey; // of person sending money
    public InputTransaction(Hash h, int n, String s, String p){
        hash = h;
        this.n=n;
        signature=s;
        publicKey=p;
    }

    public String toString(){
      return "" + hash.toString();
    }

    protected InputTransaction clone() throws CloneNotSupportedException {
      return new InputTransaction(
        hash.clone(),
        n,
        new String(signature),
        new String(publicKey)
      );
    }
}
