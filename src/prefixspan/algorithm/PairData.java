
package prefixspan.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Raúl Moya Reyes <raulmoya.es>
 * @author Agustín Ruiz Linares <agustruiz.es>
 */
public class PairData {
    public List<Transaction> dataBase;
    public List<Integer> indeces;

    /**
     * Class constructor
     */
    public PairData() {
        this.dataBase = new ArrayList();
        this.indeces = new ArrayList();
    }
    
    /**
     * Clean pairdata items
     */
    public void clear(){
        this.dataBase.clear();
        this.indeces.clear();
    }
}
