/**
 * Created by himanshu on 12/5/16.
 */
public class Runner {

    public static void main(String args[]){
        // you have to initialize the internet before doing anything, lol

        Client a = new Client(6);
        Client b = new Client(5);
        Client c = new Client(1);
        System.out.printf("%s has %s coins\n", a.ipaddress, a.coins);
        System.out.printf("%s has %s coins\n", b.ipaddress, b.coins);
        System.out.printf("%s has %s coins\n", c.ipaddress, c.coins);

        System.out.printf("Transferring 3 coins from %s to %s\n", a.ipaddress, b.ipaddress);
        a.send(b, 3);

        System.out.printf("Transferring 2 coins from %s to %s\n", a.ipaddress, b.ipaddress);
        a.send(b, 2);

        System.out.printf("Transferring 1 coins from %s to %s\n", c.ipaddress, b.ipaddress);
        c.send(b, 1);

        System.out.printf("Transferring 3 coins from %s to %s\n", b.ipaddress, a.ipaddress);
        b.send(a, 3);


        System.out.printf("a has %s coins\n", a.coins);
        System.out.printf("b has %s coins\n", b.coins);
        System.out.printf("c has %s coins\n", c.coins);

        //each knows about the transaction history
        System.out.printf("a: %s\n", a.ledger.toString());
        System.out.printf("b: %s\n", b.ledger.toString());
        System.out.printf("c: %s\n", c.ledger.toString());
        
    }
}

