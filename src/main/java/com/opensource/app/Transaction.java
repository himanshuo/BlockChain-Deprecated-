package com.opensource.app;

import com.opensource.app.lib.Hash;
import com.opensource.app.lib.Hashable;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by himanshu on 11/17/16.
 */
public class Transaction implements Hashable {
    byte [] hash;   //32 bit
    int version;
    int inputSize;
    int outputSize;
    int lock_time; //todo (himanshuo): implement lock time
    int size;  //todo (himanshuo): byte size of Transaction
    //todo (himanshuo): block_chain_configuration id


    //todo (himanshuo): Each input must have a cryptographic digital signature that unlocks the funds from the prior transaction. Only the person possessing the appropriate private key is able to create a satisfactory signature; this in effect ensures that funds can only be spent by their owners.
    ArrayList<InputTransaction> in;
    ArrayList<OutputTransaction> out;



    public Transaction(ArrayList<InputTransaction> in, ArrayList<OutputTransaction> out) throws NoSuchAlgorithmException, IOException, Hash.UnknownByteConversionException {
        assert in != null;
        assert in != null;
        this.in = in;
        this.out = out;

        //NOTE: Transaction hash is dependent on values thus can only be calculated once rest of items have been set.
        this.hash = Hash.hash(this);
    }


    public String toString(){

        return String.format("%s->%s",
                this.in.toString(),
                this.out.toString()
        );
    }

    public boolean equals(Transaction o){
        if(this == o) return true;
        if(equalHash(this.hash, o.hash)) return true;
        return false;
    }

    // todo (himanshuo): move this out of here to some Hash class or Ledger class
    private boolean equalHash(byte[] one, byte[] two) {
      if((one == null) ^ (two == null)) return false;
      if(one == null && two == null) return true;
      if(one.length != two.length) return false;
      for(int i =0; i< two.length; i++) {
        if(one[i] != two[i]) return false;
      }
      return true;
    }

    public byte[] getBytes(){
        return this.toString().getBytes();
    }
}
