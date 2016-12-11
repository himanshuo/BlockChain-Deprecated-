import lib.Hash;
import lib.Hashable;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.Map;

/**
 * Created by himanshu on 11/17/16.
 */
public class Transaction implements Hashable {
    byte [] hash;   //32 bit
    int amount;
    Client sender;
    Client receiver;
    //todo (himanshuo): block_chain_configuration id
    //todo (himanshuo): num input / num output?
    //todo (himanshuo): lock time?
    //todo (himanshuo): byte size of Transaction?

    //todo (himanshuo): in and out are very different than bitcoin protocol. Good/Bad?
    //todo (himanshuo): Each input must have a cryptographic digital signature that unlocks the funds from the prior transaction. Only the person possessing the appropriate private key is able to create a satisfactory signature; this in effect ensures that funds can only be spent by their owners.
    ArrayList<Transaction> in;
    ArrayList<Transaction> out;



    public Transaction(Client sender, Client receiver, int amount) throws NoSuchAlgorithmException, IOException, Hash.UnknownByteConversionException {
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
        //NOTE: Transaction hash is dependent on values thus can only be calculated once rest of items have been set.
        this.hash = Hash.hash(this);
    }


    public void broadcast(Transaction t){
        //send to internet so others can pick up
        for(Client c: Internet.getClientList()){
            c.ledger.add(t);
        }
    }


    public boolean submit(){
        //todo (himanshuo): validate from own ledger

        //todo (himanshuo): get validations from others
        //todo (himanshuo): accept if >50% of others say its good
        //todo (himanshuo): if success, broadcast to others
        if(this.receiver.validate(this)){
            broadcast(this);
            return true;
        }
        return false;
    }

    public String toString(){
        return String.format("%s: %s->%s",
                amount,
                sender.ipaddress,
                receiver.ipaddress
                );
    }

    public byte[] getBytes(){
        return this.toString().getBytes();
    }
}
