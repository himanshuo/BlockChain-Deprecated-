/**
 * Created by himanshu on 12/14/16.
 */
public class InputTransaction {
    byte [] hash;   //32 bit identifying hash of the input transaction
    int n;  // n=0 means first output from hash transaction
    String signature; // signature of person sending money
    String publicKey; // of person sending money
    public InputTransaction(byte[] h, int n, String s, String p){
        hash = h;
        this.n=n;
        signature=s;
        publicKey=p;
    }
}
