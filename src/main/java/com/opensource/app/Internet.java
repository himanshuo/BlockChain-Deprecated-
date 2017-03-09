package com.opensource.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by himanshu on 12/7/16.
 */
public class Internet {
    //this is a fake class that mocks the internet for BlockChain
    private static ArrayList<Client> clients = new ArrayList<Client>();
    private Internet(){}    // prevent anyone from creating another internet

//    private static Internet singleInstance;
//    private static Internet getInstance(){
//        if(singleInstance==null){
//            singleInstance = new Internet();
//        }
//        return singleInstance;
//    }


    public static BitcoinAddress registerClient(Client c, ArrayList<Transaction> ledger){
        catchUpLedger(ledger);
        clients.add(c);
        return new BitcoinAddress(String.valueOf((int)(Math.random() * 10000)));
    }
    public static ArrayList<Client> getClientList(){
        //shuffling makes it so that the order of retrievals is random
        long seed = System.nanoTime();
        Collections.shuffle(clients, new Random(seed));
        return clients;
    }

    public static void reset(){
        clients.clear();
    }

    public static boolean verify(Transaction t){
      int minRequiredValidations = clients.size()/2+1;
      int curValidations = 0;
      ArrayList<Client> hasNotValidated = new ArrayList<Client>(clients);
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

    public static void catchUpLedger(ArrayList<Transaction> ledger) {
      if(clients.size() == 0) return;
      int clientNumber = (int)(Math.random() * (clients.size()-1));
      Client client = clients.get(clientNumber);
      for(int i = 0; i < client.ledger.size(); i++) {
        ledger.add(client.ledger.get(i));
      }
    }

    public static void broadcast(Transaction t) {
      // todo (himanshuo): can only broadcast if transaction t has been validated
      for(Client c: clients){
          c.ledger.add(t);
      }
    }
}
