package prefixspan.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import prefixspan.utils.ReadFile;

/**
 * PrefixSpan algorithm
 * @author Raúl Moya Reyes <raulmoya.es>
 * @author Agustín Ruiz Linares <agustruiz.es>
 */
public class PrefixSpan {

    /*
     * Extraoficial Lanzar expeccion si se salen min_sup y max_pat
     */
    ///Minimun support
    private final int minSup;
    ///Max pattern size
    private final int maxPat;
    ///Patterns
    private final List<Integer> pattern;

    /**
     * Class constructor
     * @param minSup Minimun support
     * @param maxPat Max pattern size
     */
    public PrefixSpan(int minSup, int maxPat) {
        this.minSup = minSup;
        this.maxPat = maxPat;
        this.pattern = new ArrayList();
    }

    /**
     * Read file method
     * @param fileName Path to file
     * @param pairData Pair data
     */
    public void read(String fileName, PairData pairData) {
        String line;
        int id = 0;

        ReadFile readFile = new ReadFile(fileName);

        line = readFile.readLine();
        while (line != null) {
            Transaction transaction = new Transaction(); //transaction.clear();

            for (String item : line.split(" ")) {
                transaction.second.add(Integer.parseInt(item));//itemSets.add(Integer.parseInt(item));
            }

            transaction.first = id++;

            pairData.dataBase.add(transaction);
            pairData.indeces.add(0);

            //Next iteration
            line = readFile.readLine();
        }

    }

    /**
     * Print frequent sequential patterns
     * @param projected Pair data
     */
    public void print_pattern(PairData projected) {

        for (Integer it : pattern) {
            System.out.print(it + " ");
        }

        System.out.print("\n( ");
        for (Transaction it : projected.dataBase) {
            System.out.print(it.first + " ");
        }
        System.out.println(") : " + projected.dataBase.size());
    }

    /**
     * Run prefixspan algorithm
     * @param file Path to file
     */
    public void run(String file) {
        PairData pairData = new PairData(); // Data Base
        this.read(file, pairData);
        project(pairData);
    }

    /**
     * Project database
     * @param projected PairData to project
     */
    public void project(PairData projected) {
        if (projected.dataBase.size() < minSup) {
            return;
        }

        this.print_pattern(projected);

        if (maxPat != 0 && pattern.size() == maxPat) {
            return;
        }

        Map<Integer, Integer> mapItem = new HashMap();
        List<Transaction> dataBase = projected.dataBase;
        for (int i = 0; i < dataBase.size(); i++) {
            List<Integer> itemSet = dataBase.get(i).second;
            for (int iter = projected.indeces.get(i); iter < itemSet.size(); iter++) {
                // ++mapItem[itemSet[iter]]
                if (mapItem.get(itemSet.get(iter)) != null) {
                    mapItem.put(itemSet.get(iter), mapItem.get(itemSet.get(iter)));
                } else {
                    mapItem.put(itemSet.get(iter), 1);
                }
            }
        }

        PairData pairData = new PairData();
        List<Transaction> newDataBase = pairData.dataBase;
        List<Integer> newIndeces = pairData.indeces;

//  for (map<unsigned int, unsigned int>::iterator it_1 = map_item.begin(); it_1 != map_item.end(); it_1++) {
        for (Map.Entry<Integer, Integer> it_1 : mapItem.entrySet()) {
            for (int i = 0; i < dataBase.size(); i++) {
                Transaction transaction = dataBase.get(i);
                List<Integer> itemSet = transaction.second;
                for (int iter = projected.indeces.get(i); iter < itemSet.size(); iter++) {
                    if (Objects.equals(itemSet.get(iter), it_1.getKey())) { 
                        newDataBase.add(transaction);
                        newIndeces.add(iter + 1);
                        break;
                    }
                }
            }

            pattern.add(it_1.getKey()); // Lo mismo que arriba
            project(pairData);
            pattern.remove(pattern.size() - 1); //popback()
            pairData.clear();

        }

    }

}
