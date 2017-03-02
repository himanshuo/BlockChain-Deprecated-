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


    public static BitcoinAddress registerClient(Client c){
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
}
