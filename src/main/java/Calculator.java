import java.util.*;

public class Calculator {

    private int cost=0;

    private int sellCost;
    private int sellNumb;
    private int buyNumb;
    private int tradeNumb;

    private int maxSells;

    /**
     * Returns optimal cost for maximum sells
     * @param  sellMap  sell requests for calculation
     * @param  buyMap   buy  requests for calculation
     */
    public double calcOptimalCost(TreeMap<Integer,Integer> sellMap,TreeMap<Integer,Integer> buyMap){
    //Iterate sellers
        TreeMap<Integer,Integer> soldMap=new TreeMap<>();
        ArrayList<Integer> sellCostList=new ArrayList<>(sellMap.keySet());
        for (int sellCost:sellCostList) {

            int buyCost=0;
            int tradeNumbSum=0;
            boolean nextBuyer=false;
            int sellInd=sellCostList.indexOf(sellCost);

            //copy buyers
            TreeMap<Integer, Integer> tempBuyMap = new TreeMap(buyMap);
            Iterator<Integer> buyIter = tempBuyMap.keySet().iterator();
            if (buyIter.hasNext()) {
                buyCost = buyIter.next();
                buyNumb = tempBuyMap.getOrDefault(buyCost, 0);
            }
            tradeNumb = 0;
            //Iterate sellers with lower cost
            for(int i=sellInd;i>=0;i--) {

                sellNumb = sellMap.getOrDefault(sellCostList.get(i), 0);

                while (sellNumb != 0) {
                    //next buyer
                    if (nextBuyer) {
                        if(buyIter.hasNext()){
                            buyCost = buyIter.next();
                            buyNumb = tempBuyMap.getOrDefault(buyCost, 0);
                            nextBuyer=false;
                        }
                        else{
                            break;
                        }
                    }

                    //
                    if (buyCost < sellCost) {
                        soldMap.put(sellCost,tradeNumbSum);
                        nextBuyer=true;
                        tempBuyMap.put(buyCost,buyNumb);
                    }
                    else {
                        //trade
                        tradeNumb = min(sellNumb, buyNumb);
                        sellNumb = sellNumb - tradeNumb;
                        buyNumb = buyNumb - tradeNumb;
                        tradeNumbSum = tradeNumbSum + tradeNumb;
                    }
                    if (sellNumb == 0) {
                        soldMap.put(sellCost,tradeNumbSum);
                    }
                    if (buyNumb == 0) {
                        nextBuyer=true;
                        soldMap.put(sellCost,tradeNumbSum);
                        tempBuyMap.put(buyCost,buyNumb);
                    }

                }
            }

        }


        //search for max sells at some cost
        maxSells=0;
        ArrayList<Integer> keys= new ArrayList<>(soldMap.keySet());
        for(int i=0;i<keys.size();i++){
            sellCost = keys.get(i);
            sellNumb = soldMap.get(sellCost);
            if(maxSells<=sellNumb&&sellNumb!=0){maxSells=sellNumb;}
        }
        for(int i=0;i<keys.size();i++){
            sellCost = keys.get(i);
            sellNumb = soldMap.get(sellCost);
            if(maxSells!=sellNumb){soldMap.remove(sellCost);}
            if(sellNumb==0){soldMap.remove(sellCost);}
        }

        ArrayList<Integer> costList=new ArrayList<>(soldMap.keySet());

        //calculate average cost
        cost=0;
        for (int i=1;i<=costList.size();i++)
        {
            cost+=costList.get(i-1);
            if(i==(costList.size())){cost=cost/i;}
        }

        return (double) cost/100;
    }

    public int getMaxSells(){
        return maxSells;
    }

    private static int min(int i1, int i2){return i2<i1?i2:i1;}
}
