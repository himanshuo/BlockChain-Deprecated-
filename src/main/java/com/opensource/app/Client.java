package com.opensource.app;

import lib.Hash;
import lib.ProofOfWork;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by himanshu on 12/5/16.
 */
public class Client {
    public class InsufficientFundsException extends Exception{};

    int coins;
    //todo (himanshuo): actual ip address
    //todo (himanshuo): more durable client identification technique?
    BitcoinAddress addr;
    String signature;
    //todo (himanshuo): better done via a bitmap?
    //todo (himanshuo): need some sort of init function which makes any new clients ledge up to date with entire history of ledgers
    //todo (himanshuo): each client does have a linked list of all transactions. thus this needs to be a linked list instead of an arraylist
    ArrayList<Transaction> ledger = new ArrayList<Transaction>();
    ArrayList<Transaction> myTransactionQueue = new ArrayList<Transaction>();
    //todo (himanshuo): a separate linkedlist of pointers should exist pointing to this clients transactions


    private Client(){
        addr = Internet.registerClient(this);
        coins = 0;
        //todo (himanshuo): proper signature
        signature = addr.ipaddress;
    }


    //todo (himanshuo): Genesis Block
    //todo (himanshuo): this shouldn't exist. A new client should start with 0 coins. Some initial value could be `transferred` to it.
    public Client(int x){
        this();
        try {
            //this is a first attempt at a coinbase implementation
            OutputTransaction ot = new OutputTransaction(x, this.addr);
            ArrayList<OutputTransaction> otList = new ArrayList<OutputTransaction>();
            otList.add(ot);
            Transaction coinbase = new Transaction(new ArrayList<InputTransaction>(), otList);
            myTransactionQueue.add(coinbase);
            coins = x;
        } catch(Exception e) {
            e.printStackTrace();
        }

    }


    private boolean validQueueMember(Transaction t){
        for(OutputTransaction ot: t.out){
            if(ot.recipient.equals(this.addr)) return true;
        }
        return false;
    }

    // todo (himanshuo): A lot of this helper functions can be put together as static functions in some helper class
    private int valueOfTransactions(ArrayList<Transaction> transactions){
        int out =0;
        for(Transaction t: transactions){
            out += getAmountToMeFromTransaction(t);
        }
        return out;
    }

    private int getAmountToMeFromTransaction(Transaction t){
        for(OutputTransaction ot: t.out){
            if(ot.recipient.equals(this.addr)) return ot.value;
        }
        System.out.printf("I DO NOT EXIST AS OUTPUT OF TRANSACTION %s\n", t.toString());
        return 0;
    }

    private int getInputTransactions(int amount, ArrayList<Transaction> inputTransactions) {
        //The below is NOT true for the current implementation. I'm keeping it here for the sake of quick reference for emphemeral coding.
        //KEY IDEA: A client does not care about all the transactions. Only care about the bottom leaf nodes, where the ledger has endings.
        // filter(ledger,
        //      t =>
        //          t.in has InputTransaction such that InputTransaction.signature = this.signature &&
        //          t.out = []
        //
        // )

        int total = 0;
        Iterator<Transaction> iter = myTransactionQueue.iterator();
        while(total < amount) {
            Transaction cur = iter.next();  //todo (himanshuo): handle iter.hasNext() is false
            assert validQueueMember(cur);
            int curAmount = getAmountToMeFromTransaction(cur);
            inputTransactions.add(cur);
            total += curAmount;
        }
        return total;
    }

    private InputTransaction buildInputTransactionFromTransaction(Transaction t, int numInput) {
        byte [] hash = t.hash;
        String signature = this.signature;
        String publicKey = "";
        return new InputTransaction(
                hash,
                numInput,
                signature,
                publicKey
        );
    }


    private ArrayList<InputTransaction> buildInput(int amount, ArrayList<OutputTransaction> outputs) throws InsufficientFundsException{
        ArrayList<InputTransaction> out = new ArrayList<InputTransaction>();
        ArrayList<Transaction> inputTransactions = new ArrayList<Transaction>();
        int inputValue = getInputTransactions(amount, inputTransactions);
        if(inputValue < amount) throw new InsufficientFundsException();

        //todo (himanshuo): handle value of inputTransaction is too high - cut last transaction into 2

        for(int i =0; i<inputTransactions.size(); i++){
            Transaction cur = inputTransactions.get(i);
            out.add(buildInputTransactionFromTransaction(cur, i));
        }
        return out;
    }



    private ArrayList<OutputTransaction> buildOutput(int amount, Client recipient){
        ArrayList<OutputTransaction> out = new ArrayList<OutputTransaction>();
        out.add(new OutputTransaction(amount, recipient.addr));
        return out;
    }

    //todo (himanshuo): allow for list of recipients
    public boolean send(int amount, Client recipient){
        try {

            ArrayList<OutputTransaction> outputs = buildOutput(amount, recipient);

            ArrayList<InputTransaction> inputs = buildInput(amount, outputs);

            Transaction t = new Transaction(inputs, outputs);
            if(t.submit()){
                recipient.coins += amount;
                this.coins -= amount;
                //todo (himanshuo): remove all inputs of t from queue
            }
        // todo (himanshuo): handle exceptions in different ways, or consolidate them
        } catch (NoSuchAlgorithmException e){
            System.out.println(e.fillInStackTrace());
            return false;
        } catch (IOException e){
            System.out.println(e.fillInStackTrace());
            return false;
        } catch (Hash.UnknownByteConversionException e){
            System.out.println(e.fillInStackTrace());
            return false;
        } catch (InsufficientFundsException e){
            System.out.println(e.fillInStackTrace());
            return false;
        }
        return true;
    }

    public Transaction getTransactionFromHash(byte[] hash) {
        for(Transaction t: this.ledger){
            if(t.hash == hash) return t;
        }
        return null;
    }

    // todo (himanshuo): actually have everyone validate transactions
    public boolean validate(Transaction transaction){
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

        int inSum = 0, outSum = 0;
        ArrayList<BitcoinAddress> baList = new ArrayList<BitcoinAddress>();
        for(OutputTransaction t : transaction.out){
            outSum += t.value;
            baList.add(t.recipient);
        }

        for(InputTransaction t : transaction.in){
            Transaction fromLedger = getTransactionFromHash(t.hash);
            // 'in' transactions exist in ledger with correct hash
            if(fromLedger == null || !fromLedger.equals(transaction)) {
                return false;
            }
            //get value that this input is sending to any one of the outputs
            for(OutputTransaction ot: fromLedger.out){
                if(baList.contains(ot.recipient)){
                    inSum += ot.value;
                }
            }
        }



        //todo (himanshuo): do I know 'in' sum in hash version?
        // sum('out' transaction values) == sum('in' transaction values)
        if(outSum != inSum) return false;

        try {
            ProofOfWork.SHA256(transaction.toString(),2);
        } catch (Exception e){
            //todo (himanshuo): perhaps some exceptions should bleed through?
            System.out.println(e.fillInStackTrace());
            return false;
        }

        return true;
    }

}
