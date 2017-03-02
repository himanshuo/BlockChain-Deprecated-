package com.opensource.app;

import lib.Hash;
import lib.Hashable;

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


    public void broadcast(Transaction t){
        //send to internet so others can pick up
        for(Client c: Internet.getClientList()){
            c.ledger.add(t);
        }
    }


    public boolean submit(){
        //todo (himanshuo): validate from own ledger

        //todo (himanshuo): get validations from others
        //todo (himanshuo): accept if >50% of others say its good
        //todo (himanshuo): if success, broadcast to others
        if(true){
            broadcast(this);
            return true;
        }
        return false;
    }

    public String toString(){

        return String.format("%s->%s",
                this.in.toString(),
                this.out.toString()
        );
    }

    public boolean equals(Transaction o){
        if(this == o) return true;
        if(this.hash == o.hash) return true;
        return false;
    }

    public byte[] getBytes(){
        return this.toString().getBytes();
    }
}
