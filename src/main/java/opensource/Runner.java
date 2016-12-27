/**
 * Created by himanshu on 12/5/16.
 */
public class Runner {

    public static void main(String args[]){
        Client a = new Client(6);
        Client b = new Client(5);
        Client c = new Client(1);
        System.out.printf("%s has %s coins\n", a.addr, a.coins);
        System.out.printf("%s has %s coins\n", b.addr, b.coins);
        System.out.printf("%s has %s coins\n", c.addr, c.coins);

        System.out.printf("Transferring 3 coins from %s to %s\n", a.addr, b.addr);
        a.send(3,b);

        System.out.printf("Transferring 2 coins from %s to %s\n", a.addr, b.addr);
        a.send(2,b);

        System.out.printf("Transferring 1 coins from %s to %s\n", c.addr, b.addr);
        c.send(1,b);

        System.out.printf("Transferring 3 coins from %s to %s\n", b.addr, a.addr);
        b.send(3,a);


        System.out.printf("a has %s coins\n", a.coins);
        System.out.printf("b has %s coins\n", b.coins);
        System.out.printf("c has %s coins\n", c.coins);

        //each knows about the transaction history
        System.out.printf("a: %s\n", a.ledger.toString());
        System.out.printf("b: %s\n", b.ledger.toString());
        System.out.printf("c: %s\n", c.ledger.toString());
        
    }
}

