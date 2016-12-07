import java.util.ArrayList;

/**
 * Created by himanshu on 12/5/16.
 */
public class Client {
    int coins;
    //todo (himanshuo): actual ip address
    //todo (himanshuo): more durable client identification technique?
    String ipaddress;
    //todo (himanshuo): better done via a bitmap?
    //todo (himanshuo): need some sort of init function which makes any new clients ledge up to date with entire history of ledgers
    //todo (himanshuo): each client does have a linked list of all transactions. thus this needs to be a linked list instead of a arraylist
    ArrayList<Transaction> ledger = new ArrayList<Transaction>();

    public Client(){
        ipaddress = Internet.registerClient(this);
        coins = 0;
    }


    //todo (himanshuo): this shouldn't exist. A new client should start with 0 coins. Some initial value could be `transferred` to it.
    public Client(int x){
        this();
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
