import lib.Hash;
import lib.ProofOfWork;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by himanshu on 12/5/16.
 */
public class Client {
    int coins;
    //todo (himanshuo): actual ip address
    //todo (himanshuo): more durable client identification technique?
    String ipaddress;
    String signature;
    //todo (himanshuo): better done via a bitmap?
    //todo (himanshuo): need some sort of init function which makes any new clients ledge up to date with entire history of ledgers
    //todo (himanshuo): each client does have a linked list of all transactions. thus this needs to be a linked list instead of an arraylist
    ArrayList<Transaction> ledger = new ArrayList<Transaction>();
    //todo (himanshuo): a separate linkedlist of pointers should exist pointing to this clients transactions

    public Client(){
        ipaddress = Internet.registerClient(this);
        coins = 0;
        //todo (himanshuo): proper signature
        signature = ipaddress;
    }


    //todo (himanshuo): Genesis Block
    //todo (himanshuo): Coinbase Transaction
    //todo (himanshuo): this shouldn't exist. A new client should start with 0 coins. Some initial value could be `transferred` to it.
    public Client(int x){
        this();
        coins = x;
    }

    private ArrayList<InputTransaction> buildInput(int amount){
        ArrayList<InputTransaction> out = new ArrayList<InputTransaction>();
        int curSum = 0;
        //determine which Transactions I can output from
        Transaction myMostRecentTransaction; //todo (himanshuo): requires coinbase transaction
        for(int i = ledger.size()-1; i >= 0; i--){
            Transaction curTransaction = ledger.get(i);
            for(int j=0; j<curTransaction.out.size(); j++) {
                if(curTransaction.out.get(j).)
            }
        }

    }


    public boolean send(Client toSend, int amount){
        try {
            Transaction t = new Transaction(this, toSend, amount);
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
