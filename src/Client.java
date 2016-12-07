/**
 * Created by himanshu on 12/5/16.
 */
public class Client {
    int coins;
    public Client(int x){
        coins = x;
    }

    public boolean send(Client toSend, int amount){
        Transaction t = new Transaction(this, toSend, amount);
        if(t.submit()){
          toSend.coins += amount;
          this.coins -= amount;
         }

        return true;
    }
}
