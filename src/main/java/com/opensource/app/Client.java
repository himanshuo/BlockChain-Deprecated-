package com.opensource.app;

import com.opensource.app.lib.ProofOfWork;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.logging.Level;

//todo (himanshuo): many of the functions in this class should be methods of the Transaction class, which has access to input/output transactions

/**
 * Created by himanshu on 12/5/16.
 */
public class Client {
    public class InsufficientFundsException extends Exception{};
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    int coins;
    BlockchainAddress addr;
    String signature;
    String publicKey;
    String privateKey;
    ArrayList<Transaction> myTransactionQueue;
    //todo (himanshuo): Client IS-A Miner

    public Client() {
        Internet.registerClient(this);
        coins = 0;
        //todo (himanshuo): proper signature
        publicKey = "publicKey for " + addr.ipaddress;
        privateKey = "privateKey for " + addr.ipaddress;
        signature = publicKey;
        myTransactionQueue = new ArrayList<Transaction>();
    }

    private int getInputTransactions(int amount, ArrayList<Transaction> inputTransactions) throws InsufficientFundsException {
        //The below is NOT true for the current implementation. I'm keeping it here for the sake of quick reference for ephemeral coding.
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
            if(iter.hasNext()) {
              Transaction cur = iter.next();
              int curAmount = cur.getAmountToClient(this);
              inputTransactions.add(cur);
              total += curAmount;
            } else {
              throw new InsufficientFundsException();
            }
        }
        return total;
    }

    private InputTransaction buildInputTransactionFromTransaction(Transaction t, int numInput) {
        Hash hash = t.hash;
        String signature = this.signature;
        String publicKey = "";
        return new InputTransaction(
                hash,
                numInput,
                signature,
                publicKey
        );
    }


    private ArrayList<InputTransaction> buildInput(int amount, ArrayList<OutputTransaction> outputs) throws InsufficientFundsException {
        ArrayList<InputTransaction> out = new ArrayList<InputTransaction>();
        ArrayList<Transaction> inputTransactions = new ArrayList<Transaction>();
        int inputValue = getInputTransactions(amount, inputTransactions);
        if(inputValue < amount) throw new InsufficientFundsException();

        int valueAdded = 0;
        for(int i = 0; i<inputTransactions.size(); i++) {
            Transaction cur = inputTransactions.get(i);
            valueAdded += cur.getAmountToClient(this);
            out.add(buildInputTransactionFromTransaction(cur, i));
        }
        if(valueAdded > amount) {
          OutputTransaction outputToSelf = new OutputTransaction(valueAdded - amount, this.addr);
          outputs.add(outputToSelf);
        }
        return out;
    }


    private ArrayList<OutputTransaction> buildOutput(int amount, Client recipient) {
        ArrayList<OutputTransaction> out = new ArrayList<OutputTransaction>();
        out.add(new OutputTransaction(amount, recipient.addr));
        return out;
    }

    public void broadcast(Transaction t){
        //send to internet so others can pick up
        Internet.broadcast(t);
    }

    public boolean submit(Transaction t) {
        //todo (himanshuo): validate from own ledger

        //todo (himanshuo): get validations from others
        //todo (himanshuo): accept if >50% of others say its good
        //todo (himanshuo): if success, broadcast to others
        if(Internet.verify(t)) {
            broadcast(t);
            return true;
        }
        return false;
    }

    //todo (himanshuo): allow for list of recipients
    public boolean send(int amount, Client recipient) {
      LOGGER.log(Level.INFO, String.format("%s ->%d-> %s\n", this.addr.toString(), amount, recipient.addr.toString()));
      try {
          ArrayList<OutputTransaction> outputs = buildOutput(amount, recipient);
          int outputCount = outputs.size();
          ArrayList<InputTransaction> inputs = buildInput(amount, outputs);
          Transaction t = new Transaction(inputs, outputs);
          if(submit(t)) {
              // remove t.inputs from this.myTransactionQueue
              LOGGER.log(Level.INFO, String.format("Removing transaction inputs %s from myTransactionQueue\n", t.in.toString()));
              for(InputTransaction inputTransaction: t.in) {
                Transaction toRemove = ledger.getTransactionFromHash(inputTransaction.hash);
                this.myTransactionQueue.remove(toRemove);
              }
              // add Transaction to self
              if (outputCount + 1 == outputs.size()) {
                LOGGER.log(Level.INFO, "Adding new transaction to myTransactionQueue because contained self as output");
                this.myTransactionQueue.add(t);
              }
              // todo (himanshuo): this recipient logic should be decoupled. A client should have an accept function that Internet calls.
              // add t to recipient.myTransactionQueue
              LOGGER.log(Level.INFO, "Adding Transaction to recipients myTransactionQueue");
              recipient.myTransactionQueue.add(t);
              // handle coin values
              LOGGER.log(Level.INFO, "Updating coin values for self and recipient");
              recipient.coins += amount;
              this.coins -= amount;
              LOGGER.log(Level.INFO, String.format("Success: %s ->%d-> %s\n", this.addr.toString(), amount, recipient.addr.toString()));
              return true;
          } else {
              LOGGER.log(Level.INFO, String.format("Failed: %s ->%d-> %s\n", this.addr.toString(), amount, recipient.addr.toString()));
              return false;
          }
      } catch (NoSuchAlgorithmException | IOException | InsufficientFundsException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e.fillInStackTrace());
            return false;
      }
    }

}
