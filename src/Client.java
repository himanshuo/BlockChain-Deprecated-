import java.util.ArrayList;

/**
 * Created by himanshu on 12/5/16.
 */
public class Client {
    int coins;
    //todo (himanshuo): better done via a bitmap?
    //todo (himanshuo): need some sort of init function which makes any new clients ledge up to date with entire history of ledgers
    //todo (himanshuo): each client does have a linked list of all transactions. thus this needs to be a linked list instead of a arraylist
    ArrayList<Transaction> ledger = new ArrayList<Transaction>();

    public Client(int x){
        coins = x;
    }


    public boolean send(Client toSend, int amount){
        Transaction t = Transaction.generate(this, toSend, amount);
        if(t.submit()){
          toSend.coins += amount;
          this.coins -= amount;
         }

        return true;
    }
}
