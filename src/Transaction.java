import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.Map;

/**
 * Created by himanshu on 11/17/16.
 */
public class Transaction {
    String id;
    ArrayList<Double> list = new ArrayList<>();




    private Transaction(){}

    public Transaction generate(){
        //todo(himanshuo): uniqueness should not be in memory

        double r = Math.random();
        while(list.contains(r)){
            r = Math.random();
        }
        list.add(r);

        Transaction out = new Transaction();
        out.id = "" + r;
        return out;
    }
}
