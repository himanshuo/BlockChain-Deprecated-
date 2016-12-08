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


    public boolean validate(Transaction transaction){
        /*todo (himanshuo):
        Validating transaction requires
              1) 'in' transactions have to exist in client's ledger
              2) 'in' transactions have to have correct hash
              3) sum('out' transaction values) <= sum('in' transactions values)
              4)
         More advanced Validation requires
              1) make it computationally costly for network users to validate transactions
                  1.1) hash function
                  1.2) can configure hash function
              2) reward those who validate transactions
                  2.1) lottery system of giving reward only to 1st client which validates (Internet randomization helps with this)
        */

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
        return outSum <= inSum;
    }
}
