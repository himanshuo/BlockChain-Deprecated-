/**
 * Created by himanshu on 12/5/16.
 */
public class Runner {
    public static void main(String args[]){
        Client a = new Client(2.4); //a has 2.4 bitcoin
        Client b = new Client(5);   //b has 5 bitcoin
        Client c = new Client(2);   //b has 5 bitcoin
        a.send(b, 3);

        //a has 5.4 bitcoin
        //b has 2 bitcoin
        //c has 2 bitcoin
        //each knows about the transaction history


        
    }
}

