import lib.Hash;
import lib.ProofOfWork;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created by himanshu on 12/5/16.
 */
public class Client {
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

    int coins;

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
            System.out.println(e.fillInStackTrace());
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
    }

    private ArrayList<Transaction> getInputTransactions(int amount) {
        //The below is NOT true for the current implementation. I'm keeping it here for the sake of quick reference for emphemeral coding.
        //KEY IDEA: A client does not care about all the transactions. Only care about the bottom leaf nodes, where the ledger has endings.
        // filter(ledger,
        //      t =>
        //          t.in has InputTransaction such that InputTransaction.signature = this.signature &&
        //          t.out = []
        //
        // )

        ArrayList<Transaction> out = new ArrayList<Transaction>();
        int total = 0;
        Iterator<Transaction> iter = myTransactionQueue.iterator();
        while(total < amount) {
            Transaction cur = iter.next();  //todo (himanshuo): handle iter.hasNext() is false
            assert validQueueMember(cur);
            int curAmount = getAmountToMeFromTransaction(cur);
            out.add(cur);
            total += curAmount;
        }
        return out;
    }

    //todo (himanshuo): handle giving value to yourself when there is extra remaining
    //todo (himanshuo):
    private ArrayList<InputTransaction> buildInput(int amount, BitcoinAddress){
        ArrayList<InputTransaction> out = new ArrayList<InputTransaction>();
        ArrayList<Transaction> inputTransactions = getInputTransactions(amount);
        for(int i =0; i<inputTransactions.size(); i++){
            Transaction cur = inputTransactions.get(i);
            byte [] hash = cur.hash;
            int n = i;
            String signature = this.signature;
            String publicKey = "";
            out.add(new InputTransaction(
                    hash,
                    i,
                    signature,
                    publicKey
            ));
        }

    }



    private ArrayList<InputTransaction> buildOutput(int amount, BitcoinAddress recipient){
        ArrayList<InputTransaction> out = new ArrayList<InputTransaction>();

    }


    public boolean send(Client toSend, int amount){
        try {
            ArrayList<Transaction> transactionsToUse = getInputTransactions(amount);
            assert
            ArrayList<InputTransaction> inputs = buildInput(amount);


            //todo (himanshuo): build output transaction list
            Transaction t = new Transaction(, buildOutput(amount, recipient));
            if(t.submit()){
                toSend.coins += amount;
                this.coins -= amount;
            }
        } catch (NoSuchAlgorithmException e){
            System.out.println(e.fillInStackTrace());
            return false;
        } catch (IOException e){
            System.out.println(e.fillInStackTrace());
            return false;
        } catch (Hash.UnknownByteConversionException e){
            System.out.println(e.fillInStackTrace());
            return false;
        }
        return true;
    }


    public boolean validate(Transaction transaction){
        /*
        Validating transaction requires
              1) 'in' transactions have to exist in client's ledger
              2) 'in' transactions have to have correct hash
              3) sum('out' transaction values) <= sum('in' transactions values)
         More advanced Validation requires
              1) make it computationally costly for network users to validate transactions
                  1.1) hash function
                  1.2) can configure hash function
              2) reward those who validate transactions
                  2.1) lottery system of giving reward only to 1st client which validates (Internet randomization helps with this)
        */
        //todo (himanshuo) : Verifications should be registered

        int inSum = 0, outSum = 0;
        for(Transaction t : transaction.in){
            //todo (himanshuo): 'contains' needs to be based on hashs of transactions
            // 'in' transactions exist in ledger with correct hash
            if(!this.ledger.contains(t)) {
                return false;
            }
            inSum += t.amount;
        }

        for(Transaction t : transaction.out){
            outSum += t.amount;
        }

        //todo (himanshuo): do I know 'in' sum in hash version?
        // sum('out' transaction values) <= sum('in' transaction values)
        if(outSum > inSum) return false;

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
