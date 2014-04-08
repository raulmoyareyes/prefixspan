package prefixspan.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import prefixspan.utils.ReadFile;

/**
 *
 * @author Raúl Moya Reyes <raulmoya.es>
 * @author Agustín Ruiz Linares <agustruiz.es>
 */
public class PrefixSpan {

    /**
     * Extraoficial Lanzar expeccion si se salen min_sup y max_pat
     */
    private final int minSup;
    private final int maxPat;
    private List<Integer> pattern;

    public PrefixSpan(int minSup, int maxPat) {
        this.minSup = minSup;
        this.maxPat = maxPat;
    }

    public void read(String fileName, PairData pairData) {
        String line;
        int id = 0;

        ReadFile readFile = new ReadFile(fileName);
        Transaction transaction = new Transaction();

        line = readFile.readLine();
        while (line != null) {
            transaction.second.clear(); //No sabemos qué hace esto (Jorl!)
            List<Integer> itemSets = transaction.second;

            for (String item : line.split(" ")) {
                itemSets.add(Integer.parseInt(item));
            }

            transaction.first = id++;

            pairData.dataBase.add(transaction);
            pairData.indeces.add(0);

            //Next iteration
            line = readFile.readLine();
        }

    }

    public void print_pattern(PairData projected) {

    }

    public void run(String file) {
        PairData pairData = new PairData();
        this.read(file, pairData);
        project(pairData);
    }

    public void project(PairData projected) {
        if (projected.dataBase.size() < minSup) {
            return;
        }

        print_pattern(projected);

        if (maxPat != 0 && pattern.size() == maxPat) {
            return;
        }
        
        Map<Integer, Integer> mapItem = new HashMap();
        List<Transaction> dataBase = projected.dataBase;
        for (int i = 0; i<dataBase.size(); ++i) {
            List<Integer> itemSet = dataBase.get(i).second;
            for(int iter = projected.indeces.get(i); iter<itemSet.size(); ++iter){
                // ++mapItem[itemSet[iter]]
                if(mapItem.get(itemSet.get(iter)) != null){
                    mapItem.put(itemSet.get(iter),mapItem.get(itemSet.get(iter)));
                }else{
                    mapItem.put(itemSet.get(iter),1);
                }
            }
        }
        
        PairData pairData = new PairData();
        List<Transaction> newDataBase = pairData.dataBase;
        List<Integer> newIndeces = pairData.indeces;
        // vamos por la línea 76
//        for (Map){
//            
//        }
 
    }

}
