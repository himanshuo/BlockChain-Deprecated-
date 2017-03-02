package com.opensource.app;

/**
 * Created by himanshu on 12/14/16.
 */
public class OutputTransaction {
    int value;  // todo (himanshuo): double
    BitcoinAddress recipient;
    public OutputTransaction(int v, BitcoinAddress ba){
        value=v;
        recipient=ba;
    }
}
