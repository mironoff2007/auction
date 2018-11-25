
import java.io.IOException;
import java.util.*;

public class Auction {

    private double cost;
    private String fileName;

    FileParser fileParser;

    public Auction(String fileName) {
        this.fileName = fileName;
        fileParser = new FileParser(fileName);
    }


    public void setWorkingDir(String workingDir) {
        fileParser.setWorkingDir( workingDir);
    }

    public String getResult() throws IOException {
        TreeMap<Integer,Integer> sellMap=new TreeMap<>(); //key-cost*100,value-quantity
        TreeMap<Integer,Integer> buyMap =new TreeMap<>(); //key-cost*100,value-quantity

        //Parse requests from file

        fileParser.Parse(sellMap,buyMap);


        //Calculate optimal cost for maximum sells
        Calculator calculator = new Calculator();
        cost=calculator.calcOptimalCost(sellMap,buyMap);
        int maxSells=calculator.getMaxSells();

        //Print result
        if(cost==0){return "0 n/a";}
        else {return(maxSells+" "+String.format(Locale.ROOT, "%.2f", cost));}

    }

    public static void main(String args[])  {

        Auction auction = new Auction("1.txt");
        try {
            System.out.println(auction.getResult());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}