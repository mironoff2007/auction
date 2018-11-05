
import java.util.*;

public class Auction {

    private double coast;
    private String fileName;

    FileParser fileParser;

    public Auction(String fileName) {
        this.fileName = fileName;
        fileParser = new FileParser(fileName);
    }


    public void setWorkingDir(String workingDir) {
        fileParser.setWorkingDir( workingDir);
    }

    public String getResult(){
        TreeMap<Integer,Integer> sellMap=new TreeMap<>(); //key-coast*100,value-quantity
        TreeMap<Integer,Integer> buyMap =new TreeMap<>(); //key-coast*100,value-quantity

        //Parse requests from file

        fileParser.Parse(sellMap,buyMap);


        //Calculate optimal coast for maximum sells
        Calculator calculator = new Calculator();
        coast=calculator.calcOptimalCoast(sellMap,buyMap);
        int maxSells=calculator.getMaxSells();

        //Print result
        if(coast==0){return "0 n/a";}
        else {return(maxSells+" "+String.format(Locale.ROOT, "%.2f", coast));}

    }

    public static void main(String args[]){

        Auction auction = new Auction("1.txt");
        System.out.println(auction.getResult());

    }
}