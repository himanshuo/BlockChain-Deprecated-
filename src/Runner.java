/**
 * Created by himanshu on 12/5/16.
 */
public class Runner {
    public static void main(String args[]){
        // you have to initialize the internet before doing anything, lol

        Client a = new Client(6);
        Client b = new Client(5);
        Client c = new Client(1);
        System.out.printf("a has %s coins\n", a.coins);
        System.out.printf("b has %s coins\n", b.coins);
        System.out.printf("c has %s coins\n", c.coins);

        System.out.println("\n\nTransferring 3 coins from a to b\n");
        a.send(b, 3);

        //a has 3 bitcoin
        //b has 8 bitcoin
        //c has 1 bitcoin
        System.out.printf("a has %s coins\n", a.coins);
        System.out.printf("b has %s coins\n", b.coins);
        System.out.printf("c has %s coins\n", c.coins);

        //each knows about the transaction history
        System.out.printf("a: %s\n", a.ledger);
        System.out.printf("b: %s\n", b.ledger);
        System.out.printf("c: %s\n", c.ledger);
        
    }
}

