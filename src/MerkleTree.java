import sun.nio.cs.UTF_32;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by himanshu on 12/8/16.
 */
public class MerkleTree {

    public class MerkleNode {

        byte [] value;


        //todo (himanshuo): probably should be a specific type of object
        public MerkleNode(Object o) throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException{
            //note: SHA-256 is 256 bits (32 bytes) long.
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(convertToBytes(o));
            this.value = md.digest();

        }

        public String toString(){
            char[] hexArray = "0123456789ABCDEF".toCharArray();
            char[] hexChars = new char[this.value.length * 2];
            for ( int j = 0; j < this.value.length; j++ ) {
                int v = this.value[j] & 0xFF;
                hexChars[j * 2] = hexArray[v >>> 4];
                hexChars[j * 2 + 1] = hexArray[v & 0x0F];
            }
            return new String(hexChars);
        }

        private Object convertFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
            try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                 ObjectInput in = new ObjectInputStream(bis)) {
                return in.readObject();
            }
        }

        private byte[] convertToBytes(Object object) throws IOException {
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 ObjectOutput out = new ObjectOutputStream(bos)) {
                out.writeObject(object);
                return bos.toByteArray();
            }
        }
    }

    MerkleNode root;


    public MerkleTree(Object r) throws NoSuchAlgorithmException, IOException{
        this.root = new MerkleNode(r);
    }

}
