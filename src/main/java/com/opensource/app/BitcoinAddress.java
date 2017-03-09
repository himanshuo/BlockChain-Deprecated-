package com.opensource.app;

/**
 * Created by himanshu on 12/14/16.
 */
public class BitcoinAddress {
    //todo (himanshuo): do you add something else to bitcoin address?
    //todo (himanshuo): add equals() method
    String ipaddress;
    public BitcoinAddress(String ip) {
        this.ipaddress = ip;
    }

    public boolean equals(Object obj) {
      if(this == obj) return true;
      if(obj instanceof BitcoinAddress) {
        BitcoinAddress other = (BitcoinAddress)obj;
        if(this.ipaddress.equals(other.ipaddress)) {
          return true;
        }
      }
      return false;
    }
}
