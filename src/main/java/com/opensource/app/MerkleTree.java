package com.opensource.app;

import sun.nio.cs.UTF_32;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lib.Hash;
/**
 * Created by himanshu on 12/8/16.
 */
public class MerkleTree {

    public class MerkleNode {

        byte [] value;


        //todo (himanshuo): probably should be a specific type of object
        public MerkleNode(Object o) throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException, Hash.UnknownByteConversionException {
            this.value = Hash.hash(o);
        }

        public String toString(){
            return Hash.toHexString(this.value);
        }


    }

    MerkleNode root;


    public MerkleTree(Object r) throws NoSuchAlgorithmException, IOException, Hash.UnknownByteConversionException{
        this.root = new MerkleNode(r);
    }

}
