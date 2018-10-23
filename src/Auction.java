import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Auction {
    private static int min(int i1, int i2){return i2<i1?i2:i1;}
    public static void main(String args[]){
    String line;
    String path="D:\\1.txt";

    double coastDouble;
    int coast;
    int sellCoast;
    int sellNumb;
    int buyNumb;
    int tradeNumb;

    int lineNumb=0;
    String [] split;


    TreeMap<Integer,Integer> sellMap=new TreeMap<>(); //key-coast*100,value-quantity
    TreeMap<Integer,Integer> buyMap =new TreeMap<>();
    try {
        BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF-8"));


        while((line=buf.readLine())!=null&&lineNumb<1000000){
            lineNumb++;
            split=line.split(" ");
            coastDouble= new Double(String.valueOf(split[2]));
            coast=(int)(coastDouble*100);
            tradeNumb=Integer.parseInt(split[1]);
            if(coast>10000){coast=10000;}
            if(coast<0){coast=0;}
            if(tradeNumb>1000){tradeNumb=1000;}
            if(tradeNumb<0){tradeNumb=0;}
            switch(split[0]){
                case "B":
                    buyMap.put(coast,tradeNumb+buyMap.getOrDefault(coast,0));
                    break;
                case "S":
                    sellMap.put(coast,tradeNumb+sellMap.getOrDefault(coast,0));
                    break;
            }
        }

    } catch (IOException e) {
        e.printStackTrace();
    }


    //iterate through TreeMap values iterator
    Iterator<Integer> buyIter= buyMap.keySet().iterator();
    for (Map.Entry<Integer, Integer> sellEntry : sellMap.entrySet()){
        sellCoast=sellEntry.getKey();
        sellNumb =sellEntry.getValue();
        sellMap.put(sellCoast,0);
        while(buyIter.hasNext()){
            coast = Integer.parseInt(buyIter.next().toString());
            buyNumb=buyMap.get(coast);
            if(coast>=sellCoast) {
                tradeNumb = min(sellNumb, buyNumb);
                    sellMap.put(sellCoast, tradeNumb+sellMap.get(sellCoast));//sold
                    buyMap.put(coast, buyNumb-tradeNumb);//buyer balance
                    sellNumb = sellNumb - tradeNumb;//seller balance
                if(sellNumb==0) {break;}
            }
            else{sellMap.put(coast, 0);break;}
        }
    }

    //search for max sells at some coast
        int maxSells=0;
        Set<Integer> keys= new HashSet<>(sellMap.keySet());
        for(Object key:keys){
            sellCoast =  Integer.parseInt(key.toString());
            sellNumb = sellMap.get(sellCoast);
            if(maxSells<=sellNumb&&sellNumb!=0){maxSells=sellNumb;}
            else{sellMap.remove(sellCoast);}
        }
        ArrayList<Integer> coastList=new ArrayList<>(sellMap.keySet());

        //calc average coast
        coast=0;
        for (int i=1;i<=coastList.size();i++)
        {
            coast+=coastList.get(i-1);
            if(i==(coastList.size())){coast=coast/i;}
        }
        if(coast==0){System.out.println("0 n/a");}
        else {System.out.println(String.format(Locale.ROOT, "%.2f", (double) coast/100)+" "+maxSells);}

    }
}
