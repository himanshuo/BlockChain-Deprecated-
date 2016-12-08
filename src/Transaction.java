import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.Map;

/**
 * Created by himanshu on 11/17/16.
 */
public class Transaction {
    String hash;
    int amount;
    Client sender;
    Client receiver;
    //todo (himanshuo): block chain version?
    //todo (himanshuo): num input / num output?
    //todo (himanshuo): lock time?
    //todo (himanshuo): byte size of Transaction?

    //todo (himanshuo): in and out are very different than bitcoin protocol. Good/Bad?
    //todo (himanshuo): Each input must have a cryptographic digital signature that unlocks the funds from the prior transaction. Only the person possessing the appropriate private key is able to create a satisfactory signature; this in effect ensures that funds can only be spent by their owners.
    ArrayList<Transaction> in;
    ArrayList<Transaction> out;


    private boolean isUniqueHash(String hash, ArrayList<Transaction> ledger){
        for(Transaction t: ledger){
            if(t.hash.equals(hash)) return false;
        }
        return true;
    }

    //todo (himanshuo): instead of id, should be using hash of transaction
    private String getUniqueId(){
        //todo (himanshuo): uniqueness should not be in memory
        //todo (himanshuo): hash function to generate unique id

        String potentialId = String.valueOf(Math.random());
        while(!isUniqueHash(potentialId, sender.ledger)){
            potentialId = String.valueOf(Math.random());
        }
        return potentialId;
    }

    private Transaction(Client sender, Client receiver, int amount){
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
        //todo (himanshuo): generate id first?
        this.hash = getUniqueId();
    }

    //todo (himanshuo): this creates a central bank. Do not want this.
    public static Transaction generate(Client sender, Client receiver, int amount){
        Transaction out = new Transaction(sender, receiver, amount);
        return out;
    }

    public void broadcast(Transaction t){
        //send to internet so others can pick up
        for(Client c: Internet.getClientList()){
            c.ledger.add(t);
        }
    }


    public boolean submit(){
        //todo (himanshuo): validate from own ledger

        //todo (himanshuo): get validations from others
        //todo (himanshuo): accept if >50% of others say its good
        //todo (himanshuo): if success, broadcast to others
        if(true){
            broadcast(this);
            return true;
        }
        return false;
    }

    public String toString(){
        return String.format("%s: %s->%s",
                amount,
                sender.ipaddress,
                receiver.ipaddress
                );
    }
}
