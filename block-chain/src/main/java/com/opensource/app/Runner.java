/**
 * Created by himanshu on 12/5/16.
 */
public class Runner {

    public static void main(String args[]){
        Client a = new Client(6);
        Client b = new Client(5);
        Client c = new Client(1);
        System.out.printf("A has %s coins\n", a.coins);
        System.out.printf("B has %s coins\n", b.coins);
        System.out.printf("C has %s coins\n", c.coins);

        System.out.printf("Transferring 3 coins from A to B\n");
        a.send(3,b);

        System.out.printf("Transferring 2 coins from A to B\n");
        a.send(2,b);

        System.out.printf("Transferring 1 coins from C to B\n");
        c.send(1,b);

        System.out.printf("Transferring 3 coins from B to A\n");
        b.send(3,a);


        System.out.printf("A has %s coins\n", a.coins);
        System.out.printf("B has %s coins\n", b.coins);
        System.out.printf("C has %s coins\n", c.coins);

        //each knows about the transaction history
        System.out.printf("A: %s\n", a.ledger.toString());
        System.out.printf("B: %s\n", b.ledger.toString());
        System.out.printf("C: %s\n", c.ledger.toString());
        
    }
}

