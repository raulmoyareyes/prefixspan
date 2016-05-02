package prefixspan.algorithm;

import java.util.*;

import com.sun.org.apache.xpath.internal.SourceTree;
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
    private final List<String> pattern;
    private int cnt;
    private List<Integer>[] record;
    /**
     * Class constructor
     * @param minSup Minimun support
     * @param maxPat Max pattern size
     */
    public PrefixSpan(int minSup, int maxPat) {
        this.minSup = minSup;
        this.maxPat = maxPat;
        this.pattern = new ArrayList();
        this.cnt=0;
        this.record = new ArrayList[100];
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
            record[id] = new ArrayList<Integer>();
            String tmp="";
            Boolean flag= false;
            for(int i=0;i<line.length();i++){

                if(line.charAt(i)!='('&&line.charAt(i)!=')'){
                    if(flag==false){
                        tmp+=line.charAt(i);
                        transaction.second.add(tmp);
                        tmp="";

                        record[id].add(1);
                    }else{
                        tmp+=line.charAt(i);
                    }

                }else if(line.charAt(i)=='('){
                    flag=true;
                }else{
                    for(int j=0;j<tmp.length();j++)
                    transaction.second.add(""+tmp.charAt(j));
                    record[id].add(tmp.length());
                    flag=false;
                    tmp="";

                }
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
        if(pattern.isEmpty())
            return;
        if(cnt==1)
            return;
        String print=pattern.get(0);
        for(int it=1;it<pattern.size();it++){
            if(pattern.get(it).charAt(0)=='_') {
                print += pattern.get(it).charAt(1);
            }else{
                if(print.length()>1){
                    System.out.print('(');
                }
                System.out.print(print + " ");
                if(print.length()>1){
                    System.out.print(')');
                }
                print=pattern.get(it);
            }

        }
        if(print.length()>1){
            System.out.print('(');
        }
        System.out.print(print);
        if(print.length()>1){
            System.out.print(')');
        }


        System.out.print("\n( ");
        int[] vis = new int[100];
        for(int i=0;i<vis.length;i++){
            vis[i]=0;
        }
        int len=0;
        for (Transaction it : projected.dataBase) {
            if(vis[it.first]==0) {
                System.out.print(it.first + " ");
                vis[it.first]=1;
                len++;
            }
        }
        System.out.println(") : " + len);
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
        int[] vis = new int[100];
        for(int i=0;i<vis.length;i++){
            vis[i]=0;
        }
        int len=0;
        for(int i=0;i<projected.dataBase.size();i++){
            Transaction t = projected.dataBase.get(i);
            if(vis[t.first]==0){
                len++;
                vis[t.first]=1;
            }
        }
        if (len < minSup) {
            return;
        }
        cnt++;
        this.print_pattern(projected);

        if (maxPat != 0 && pattern.size() == maxPat) {
            return;
        }

        Map<String, Integer> mapItem = new HashMap();
        List<Transaction> dataBase = projected.dataBase;
        for (int i = 0; i < dataBase.size(); i++) {
            List<String> itemSet = dataBase.get(i).second;
            Transaction trans = dataBase.get(i);
            int id = trans.first;
            for (int iter = projected.indeces.get(i); iter < itemSet.size(); iter++) {
                // ++mapItem[itemSet[iter]]
                String mine="";
                int low=0,high=0;
                for(int j=0;j<record[id].size();j++){
                    low=high;
                    high+=record[id].get(j);
                    if(iter>=low&&iter<high&&projected.indeces.get(i)-1>=low&&projected.indeces.get(i)<high&&record[id].get(j)>1){
                        mine+="_";
                        break;
                    }
                }
                mine+=itemSet.get(iter);

                    if (mapItem.get(mine) != null) {
                        mapItem.put(mine, mapItem.get(mine));
                    } else {
                        mapItem.put(mine, 1);
                    }

            }
        }

        PairData pairData = new PairData();
        List<Transaction> newDataBase = pairData.dataBase;
        List<Integer> newIndeces = pairData.indeces;

//  for (map<unsigned int, unsigned int>::iterator it_1 = map_item.begin(); it_1 != map_item.end(); it_1++) {
        for (Map.Entry<String, Integer> it_1 : mapItem.entrySet()) {

            for (int i = 0; i < dataBase.size(); i++) {
                Transaction transaction = dataBase.get(i);
                List<String> itemSet = transaction.second;
                int flag=0;
                int id = transaction.first;
                for (int iter = projected.indeces.get(i); iter < itemSet.size(); iter++) {
                    String mine = itemSet.get(iter);
                   if(it_1.getKey().charAt(0)=='_'){
                      int low=0,high=0;
                       int ok=0;
                       for(int j=0;j<record[id].size();j++){
                           low=high;
                           high+=record[id].get(j);
                           if(iter>=low&&iter<high&&projected.indeces.get(i)-1>=low&&projected.indeces.get(i)<high&&record[id].get(j)>1){
                               if(mine.equals(""+it_1.getKey().charAt(1))) {
                                   newDataBase.add(transaction);
                                   newIndeces.add(iter + 1);
                                   ok=1;
                                   break;
                               }
                           }
                       }
                       if(ok==1){
                           break;
                       }
                   } else {
                       if (Objects.equals(itemSet.get(iter), it_1.getKey())) {
                           if(flag==0) {
                               newDataBase.add(transaction);
                               newIndeces.add(iter + 1);
                               flag=1;
                               int low=0,high=0;
                               int ok=0;
                               for(int j=0;j<record[id].size();j++){
                                   low=high;
                                   high+=record[id].get(j);
                                   if(iter>=low&&iter<high&&record[id].get(j)>1){
                                       ok=1;
                                       break;
                                   }
                               }
                               if(ok==1){
                                   break;
                               }

                           }else{
                               int low=0,high=0;
                               int ok=0;
                               for(int j=0;j<record[id].size();j++){
                                   low=high;
                                   high+=record[id].get(j);
                                   if(iter==low&&iter<high&&record[id].get(j)>1){
                                       ok=1;
                                       newDataBase.add(transaction);
                                       newIndeces.add(iter + 1);
                                       break;
                                   }
                               }
                               if(ok==1){
                                   break;
                               }
                           }
                        }
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
