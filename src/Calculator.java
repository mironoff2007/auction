import java.util.*;

public class Calculator {

    private int coast=0;

    private int sellCoast;
    private int sellNumb;
    private int buyNumb;
    private int tradeNumb;

    private int maxSells;

    /**
     * Returns optimal coast for maximum sells
     * @param  sellMap  sell requests for calculation
     * @param  buyMap   buy  requests for calculation
     */
    public double calcOptimalCoast(TreeMap<Integer,Integer> sellMap,TreeMap<Integer,Integer> buyMap){
    //iterate through TreeMap buyers iterator
        Iterator<Integer> buyIter= buyMap.keySet().iterator();
        if(buyIter.hasNext()){coast = Integer.parseInt(buyIter.next().toString());}
        for (Map.Entry<Integer, Integer> sellEntry : sellMap.entrySet()){
            sellCoast=sellEntry.getKey();
            sellNumb =sellEntry.getValue();
            sellMap.put(sellCoast,0);
            //iterate buyers
            while(!buyMap.isEmpty()){
                buyNumb=buyMap.getOrDefault(coast,0);
                //buyers sold all
                if(buyNumb==0){
                    if(buyIter.hasNext()){
                        coast = Integer.parseInt(buyIter.next().toString());
                        buyNumb=buyMap.getOrDefault(coast,0);}
                    else{break;}

                }
                //seller coast lower than buyer
                if(coast>=sellCoast) {
                    tradeNumb = min(sellNumb, buyNumb);
                    sellMap.put(sellCoast, tradeNumb+sellMap.get(sellCoast));//sold
                    buyMap.put(coast, buyNumb-tradeNumb);//buyer balance
                    sellNumb = sellNumb - tradeNumb;//seller balance
                    //seller sold all
                    if(sellNumb==0) { if(buyIter.hasNext()){coast = Integer.parseInt(buyIter.next().toString());}break;}
                }
                //seller coast is to high
                else{sellMap.put(coast, 0); if(buyIter.hasNext()){coast = Integer.parseInt(buyIter.next().toString());}break;}
            }
        }

        //search for max sells at some coast
        maxSells=0;
        Set<Integer> keys= new HashSet<>(sellMap.keySet());
        for(Object key:keys){
            sellCoast = Integer.parseInt(key.toString());
            sellNumb = sellMap.get(sellCoast);
            if(maxSells<=sellNumb&&sellNumb!=0){maxSells=sellNumb;}
            else{sellMap.remove(sellCoast);}
        }
        ArrayList<Integer> coastList=new ArrayList<>(sellMap.keySet());

        //calculate average coast
        coast=0;
        for (int i=1;i<=coastList.size();i++)
        {
            coast+=coastList.get(i-1);
            if(i==(coastList.size())){coast=coast/i;}
        }

        return (double) coast/100;
    }

    public int getMaxSells(){
        return maxSells;
    }

    private static int min(int i1, int i2){return i2<i1?i2:i1;}
}
