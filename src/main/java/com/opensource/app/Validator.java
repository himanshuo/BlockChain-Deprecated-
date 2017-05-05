package com.opensource.app;

public class Validator {
  public Validator(){}

  public boolean runAll(Transaction transaction, Ledger ledger, Client client){
    return validateInputExistsInLedger(transaction, ledger) &&
            validateInputOutputSumMatch(transaction, ledger) &&
            validateClientInOutput(transaction, client);
  }

  private boolean validateInputExistsInLedger(Transaction transaction, Ledger ledger){
    for(InputTransaction it: transaction.in) {
      if(!ledger.containsTransaction(it.hash)) return false;
    }
    return true;
  }

  private boolean validateInputOutputSumMatch(Transaction transaction, Ledger ledger){
    int inSum = 0;
    for(InputTransaction it: transaction.in) {
      inSum += ledger.getTransaction(it.hash).getAmount();
    }
    int outSum = 0;
    for(OutputTransaction ot: transaction.out) {
      outSum += ot.value;
    }
    return inSum == outSum;
  }


  private boolean validateClientInOutput(Transaction transaction, Client client){
    for(OutputTransaction ot: transaction.out) {
      if(ot.addr.equals(client.addr)) return true;
    }
    return false;
  }

  // todo (himanshuo): Check SIGNATURE: isvalid(transaction, publicKey of client, transaction signed value) = true/false
  
}
