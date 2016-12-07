import java.util.ArrayList;

/**
 * Created by himanshu on 12/7/16.
 */
public class Internet {
    //this is a fake class that is mocks the internet for this BlockChain protocol

    private static ArrayList<Client> clients = new ArrayList<>();
    private Internet(){}    // prevent anyone from creating another internet

//    private static Internet singleInstance;
//    private static Internet getInstance(){
//        if(singleInstance==null){
//            singleInstance = new Internet();
//        }
//        return singleInstance;
//    }


    public static void registerClient(Client c){
        clients.add(c);
    }

}
