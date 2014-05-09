package prefixspan;

import prefixspan.algorithm.PrefixSpan;
import prefixspan.utils.ReadFile;

/**
 * Main class
 *
 * @author Raúl Moya Reyes <raulmoya.es>
 * @author Agustín Ruiz Linares <agustruiz.es>
 */
public class Main {

    ///Params path
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Read params
        String paramsPath = "./params.txt";
        ReadFile rf = new ReadFile((paramsPath));
        String filePath = rf.readLine();
        int min_sup = Integer.parseInt(rf.readLine());
        int max_pat = Integer.parseInt(rf.readLine());
        
        //Execute algorithm
        PrefixSpan a = new PrefixSpan(min_sup, max_pat);
        a.run(filePath);
    }

    private void readParams() {
        //Read params.txt
    }

}
