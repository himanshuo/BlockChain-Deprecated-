import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.Map;

/**
 * Created by himanshu on 11/17/16.
 */
public class Transaction {
    String id;
    int amount;
    Client sender;
    Client receiver;

    Transaction previous;

    private boolean isUniqueId(String id, ArrayList<Transaction> ledger){
        for(Transaction t: ledger){
            if(t.id.equals(id)) return false;
        }
        return true;
    }
    private String getUniqueId(){
        //todo(himanshuo): uniqueness should not be in memory
        String potentialId = String.valueOf(Math.random());
        while(!isUniqueId(potentialId, sender.ledger)){
            potentialId = String.valueOf(Math.random());
        }
        return potentialId;
    }

    private Transaction(Client sender, Client receiver, int amount){
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
        //todo (himanshuo): generate id first?
        this.id = getUniqueId();
    }

    public static Transaction generate(Client sender, Client receiver, int amount){
        Transaction out = new Transaction(sender, receiver, amount);
        return out;
    }

    public void broadcast(Transaction t){
        //send to internet so others can pick up
        Internet.addTransaction(t);

    }

    // actually does the transferring, broadcasting, ...
    public boolean submit(){
        broadcast(this);
        return true;
    }
}
