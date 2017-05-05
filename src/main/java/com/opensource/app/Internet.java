package com.opensource.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.lang.CloneNotSupportedException;

/**
 * Created by himanshu on 12/7/16.
 */
public class Internet {
    //this is a fake class that mocks the internet for BlockChain
    private static ArrayList<Miner> miners = new ArrayList<Miner>();
    private Internet(){}    // prevent anyone from creating an internet

    public static void register(Miner miner){
      try {
        miner.ledger = getLedger();
      } catch (CloneNotSupportedException e) {
        LOGGER.log(Level.INFO, e.toString(), e.fillInStackTrace());
        miner.ledger = new Ledger();
      }

        miner.addr = new BlockchainAddress(String.valueOf((int)(Math.random() * 10000)));
        miners.add(miner);
    }

    public static ArrayList<Miner> getMinerList(){
        //shuffling makes it so that the order of retrievals is random
        long seed = System.nanoTime();
        Collections.shuffle(miners, new Random(seed));
        return miners;
    }

    public static void reset(){
        miners.clear();
    }

    public static boolean verify(Transaction t){
      int minRequiredValidations = miners.size()/2+1;
      int curValidations = 0;
      ArrayList<Miner> hasNotValidated = new ArrayList<Miner>(miners);
      Collections.shuffle(hasNotValidated);
      for(int i = 0; i < hasNotValidated.size(); i++) {
        boolean isValid = hasNotValidated.get(i).validate(t);
        if(isValid) {
          curValidations++;
          if(curValidations == minRequiredValidations) return true;
        }
      }
      return false;
    }

    public static Ledger getLedger() throws CloneNotSupportedException {
      // todo (himanshuo): Genesis block
      if(miners.size() == 0) return null;

      int minerNumber = (int)(Math.random() * (miners.size()-1));
      Miner miner = miners.get(minerNumber);
      return miner.ledger.clone();
    }

    public static void broadcast(Transaction t) {
      // todo (himanshuo): can only broadcast if transaction t has been validated
      // for(Miner c: miners){
      //     c.ledger.add(t);
      // }
      return;
    }
}
