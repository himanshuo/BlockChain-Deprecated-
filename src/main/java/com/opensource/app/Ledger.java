package com.opensource.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.lang.CloneNotSupportedException;

public class Ledger{
  // todo (himanshuo): better done via a bitmap?
  // todo (himanshuo): need some sort of init function which makes any new clients ledge up to date with entire history of ledgers
  // todo (himanshuo): each client does have a linked list of all transactions. thus this needs to be a linked list instead of an arraylist
  // todo (himanshuo): tree of TransactionBlock
  private ArrayList<TransactionBlock> ledger;

  public Ledger() {
      ledger = new ArrayList<TransactionBlock>();
  }

  public Transaction getTransaction(Hash hash) {
    for(TransactionBlock tb: ledger) {
      if(tb.contains(hash)) {
        return tb.get(hash);
      }
    }
    return null;
  }

  public boolean containsTransaction(Hash hash) {
    for(TransactionBlock tb: ledger) {
      if(tb.contains(hash)) {
        return true;
      }
    }
    return false;
  }


  public boolean add(TransactionBlock tb) throws IllegalArgumentException {
    if(this.ledger.contains(tb)) throw new IllegalArgumentException();
    this.ledger.add(tb);
    return true;
  }

  public boolean equals(Ledger other) {
    if(this == other) return true;
    return this.ledger.equals(other.ledger);
  }

  public String toString() {
      String out = "Ledger[";
      for(TransactionBlock t: ledger) {
        out += t.toString() + " ";
      }
      return out + "]";
  }

  protected Ledger clone() throws CloneNotSupportedException {
      Ledger ledgerClone = new Ledger();
      for(TransactionBlock tb: ledger) {
        TransactionBlock transactionBlockClone = tb.clone();
        ledgerClone.add(transactionBlockClone);
      }
      return ledgerClone;
  }

// todo (himanshuo): iterator instead of giving out all transactions as list
  // Iterator<Transaction> iterator(){
  //   return new LedgerIterator<Transaction>();
  // }

  public ArrayList<Transaction> getTransactions() {
    ArrayList<Transaction> out = new ArrayList<Transaction>();
    for(TransactionBlock tb: ledger) {
      for(Transaction t: tb.block) {
        out.add(t);
      }
    }
    return out;
  }

}
