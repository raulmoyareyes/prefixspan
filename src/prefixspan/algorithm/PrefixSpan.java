package prefixspan.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    private final List<Integer> pattern;

    public PrefixSpan(int minSup, int maxPat) {
        this.minSup = minSup;
        this.maxPat = maxPat;
        this.pattern = new ArrayList();
    }

    public void read(String fileName, PairData pairData) {// Comprobado por Raúl, parece que funciona bien
        String line;
        int id = 0;

        ReadFile readFile = new ReadFile(fileName);
//        Transaction transaction = new Transaction();

        line = readFile.readLine();
        while (line != null) {
            Transaction transaction = new Transaction(); //transaction.clear(); //vacía la transaccion para crear una nueva
//            List<Integer> itemSets = transaction.second;

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

    public void run(String file) {
        PairData pairData = new PairData(); // Data Base
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
                    if (Objects.equals(itemSet.get(iter), it_1.getKey())) { //it_1.first creo que está mal sería value
                        newDataBase.add(transaction);
                        newIndeces.add(iter + 1);
                        break; // ??? WTF
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
