package com.opensource.app;

import java.util.ArrayList;

public class TransactionBlock {
  ArrayList<Transaction> block;
  public TransactionBlock() {
      block = new ArrayList<Transaction>();
  }

  public boolean add(Transaction t){
    block.add(t);
    return true;
  }

  public boolean sign(){
    return true;
  }

  boolean contains(Hash hash) {
    for(Transaction t: block) {
      if(t.hash.equals(hash)) return true;
    }
    return false;
  }

  Transaction get(Hash hash) {
    for(Transaction t: block) {
      if(t.hash.equals(hash)) return t;
    }
    return null;
  }

  protected TransactionBlock clone() throws CloneNotSupportedException {
    TransactionBlock clone = new TransactionBlock();
    for(Transaction t: block) {
      clone.add(t.clone());
    }
    return clone;
  }

}
