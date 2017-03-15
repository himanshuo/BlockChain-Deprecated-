package com.opensource.app;

import java.util.ArrayList;

public class Miner{


  public Miner(){}

  //todo (himanshuo): Genesis Block


  // public void coinbase(){
  //   OutputTransaction ot = new OutputTransaction(x, this.addr);
  //   ArrayList<OutputTransaction> otList = new ArrayList<OutputTransaction>();
  //   otList.add(ot);
  //   Transaction coinbase = new Transaction(new ArrayList<InputTransaction>(), otList);
  //   broadcast(coinbase);
  //   myTransactionQueue.add(coinbase);
  // }

  boolean mine(ArrayList<Transaction> unvalidatedTransactions){

  }

  private Transaction determineEdge() {

  }

  // todo (himanshuo): actually have everyone validate transactions
  public boolean validate(Transaction transaction) {
      /*
      Validating transaction requires
            1) 'in' transactions have to exist in client's ledger
            2) 'in' transactions have to have correct hash
            3) sum('out' transaction values) == sum('in' transactions values)
       More advanced Validation requires
            1) make it computationally costly for network users to validate transactions
                1.1) hash function
                1.2) can configure hash function
            2) reward those who validate transactions
                2.1) lottery system of giving reward only to 1st client which validates (Internet randomization helps with this)
      */
      //todo (himanshuo) : Verifications should be registered
      LOGGER.log(Level.INFO, "Starting Validate");
      int inSum = 0, outSum = 0;
      ArrayList<BlockchainAddress> baList = new ArrayList<BlockchainAddress>();
      for(OutputTransaction t : transaction.out){
          outSum += t.value;
          baList.add(t.recipient);
      }
      LOGGER.log(Level.INFO, "OUT SUM:" + outSum);
      for(InputTransaction t : transaction.in) {
          Transaction fromLedger = getTransactionFromHash(t.hash);
          // 'in' transactions exist in ledger with correct hash
          if(fromLedger == null || !fromLedger.hash.equals(t.hash)) {
              return false;
          }

          // get value that this input is sending to any one of the outputs
          for(OutputTransaction ot: fromLedger.out) {
              inSum += ot.value;
          }
      }
      LOGGER.log(Level.INFO, "IN SUM:" + inSum);

      //todo (himanshuo): do I know 'in' sum in hash version?
      // sum('out' transaction values) == sum('in' transaction values)
      if(outSum != inSum) return false;

      try {
          ProofOfWork.SHA256(transaction.toString(), 2);
      } catch (Exception e){
          //todo (himanshuo): perhaps some exceptions should bleed through?
          LOGGER.log(Level.SEVERE, e.toString(), e.fillInStackTrace());
          return false;
      }

      return true;
  }

}
