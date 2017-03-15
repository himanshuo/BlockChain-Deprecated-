package com.opensource.app;

/**
 * Created by himanshu on 12/14/16.
 */
public class BlockchainAddress {

  //todo (himanshuo): actual ip address
  //todo (himanshuo): more durable client identification technique?

    //todo (himanshuo): do you add something else to blockchain address?
    String ipaddress;
    public BlockchainAddress(String ip) {
        this.ipaddress = ip;
    }

    public boolean equals(Object obj) {
      if(this == obj) return true;
      if(obj instanceof BlockchainAddress) {
        BlockchainAddress other = (BlockchainAddress)obj;
        return this.ipaddress.equals(other.ipaddress);
      }
      return false;
    }

    public BlockchainAddress clone(){
      return new BlockchainAddress(ipaddress);
    }
}
