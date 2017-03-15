package com.opensource.app;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.lang.CloneNotSupportedException;

/**
 * Created by himanshu on 11/17/16.
 */
public class Transaction {
    Hash hash;
    int version;
    int inputSize;
    int outputSize;
    int lock_time; //todo (himanshuo): implement lock time
    int size;  //todo (himanshuo): byte size of Transaction
    //todo (himanshuo): block_chain_configuration id
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());


    //todo (himanshuo): Each input must have a cryptographic digital signature that unlocks the funds from the prior transaction. Only the person possessing the appropriate private key is able to create a satisfactory signature; this in effect ensures that funds can only be spent by their owners.
    ArrayList<InputTransaction> in;
    ArrayList<OutputTransaction> out;



    public Transaction(ArrayList<InputTransaction> in, ArrayList<OutputTransaction> out) throws NoSuchAlgorithmException, IOException {
        assert in != null;
        assert in != null;
        this.in = in;
        this.inputSize = in.size();
        this.out = out;
        this.outputSize = out.size();
        this.version = 1;

        //NOTE: Transaction hash is dependent on values thus can only be calculated once rest of items have been set.
        this.hash = new Hash(this.toString());
        this.size = this.serialize().length;
    }


    public String toString(){
        return String.format("%s->%s",
                this.in.toString(),
                this.out.toString()
        );
    }

    public boolean equals(Transaction o){
        if(this == o) return true;
        if(this.hash.equals(o.hash)) return true;
        return false;
    }

    public byte[] serialize(){
        return this.toString().getBytes();
    }



    public int getAmount() {
      int amount = 0;
      for(OutputTransaction ot: out) {
        amount += ot.value;
      }
      return amount;
    }

    public int getAmountToClient(Client c){
        for(OutputTransaction ot: this.out){
            if(ot.recipient.equals(c.addr)) return ot.value;
        }
        LOGGER.log(Level.INFO, "Client " + c.toString() + " does not exist as output to "+ this.toString());
        return 0;
    }

    protected Transaction clone() throws CloneNotSupportedException {
      try {
        ArrayList<InputTransaction> clonedInput = new ArrayList<InputTransaction>();
        for(int i =0; i < in.size(); i++){
          clonedInput.add(in.get(i).clone());
        }
        ArrayList<OutputTransaction> clonedOutput = new ArrayList<OutputTransaction>();
        for(int i =0; i < out.size(); i++){
          clonedOutput.add(out.get(i).clone());
        }
        Transaction clone = new Transaction(clonedInput, clonedOutput);
        clone.hash = hash;
        clone.version = version;
        clone.inputSize = inputSize;
        clone.outputSize = outputSize;
        clone.lock_time = lock_time;
        clone.size = size;

        return clone;
      } catch (Exception e) {
        LOGGER.log(Level.SEVERE, e.toString(), e.fillInStackTrace());
        throw new CloneNotSupportedException();
      }

    }

}
