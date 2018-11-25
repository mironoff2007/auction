
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;


public class FileParser {

    private String line;
    private String path;
    private String fileName;
    private String workingDir;

    public void setWorkingDir(String workingDir) {
        this.workingDir = workingDir;
    }

    private double costDouble;
    private int cost=0;

    private int tradeNumb;//quantity of trade items

    private int lineNumb=0;
    private String [] split;

    public FileParser(String fileName){
        workingDir = System.getProperty("user.dir")+"\\resources\\";
        this.fileName=fileName;
    }

    /**
     * Reads file and stores data to this maps
     * @param  sellMap  sell requests to be stored in this map
     * @param  buyMap   buy  requests to be stored in this map
     */
    public void Parse(TreeMap<Integer,Integer> sellMap,TreeMap<Integer,Integer> buyMap) throws IOException {
        try {
            path=workingDir+fileName;

            BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF-8"));


            //Read lines, lines count is limited by 1000000
            while((line=buf.readLine())!=null&&lineNumb<1000000){
                lineNumb++;
                split=line.split(" ");
                costDouble= new Double(String.valueOf(split[2]));
                cost=(int)(costDouble*100);
                tradeNumb=Integer.parseInt(split[1]);
                //cost limited by 10000
                if(cost>10000){cost=10000;}
                if(cost<0){cost=0;}
                //quantity of trade items is limited by 10000
                if(tradeNumb>1000){tradeNumb=1000;}
                if(tradeNumb<0){tradeNumb=0;}
                //store buy or sell request
                switch(split[0]){
                    case "B":
                        buyMap.put(cost,tradeNumb+buyMap.getOrDefault(cost,0));
                        break;
                    case "S":
                        sellMap.put(cost,tradeNumb+sellMap.getOrDefault(cost,0));
                        break;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }


}
