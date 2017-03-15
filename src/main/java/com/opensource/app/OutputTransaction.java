package com.opensource.app;

/**
 * Created by himanshu on 12/14/16.
 */
public class OutputTransaction {
    int value;  // todo (himanshuo): double
    BlockchainAddress recipient;
    public OutputTransaction(int v, BlockchainAddress ba) {
        value=v;
        recipient=ba;
    }

    public String toString(){
      return String.format("<OutputTransaction recipientIp=%s value=%s>",recipient.ipaddress, value);
    }

    public OutputTransaction clone() {
      return new OutputTransaction(
        value,
        recipient.clone()
      );
    }
}
