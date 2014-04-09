
package prefixspan.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Raúl Moya Reyes <raulmoya.es>
 * @author Agustín Ruiz Linares <agustruiz.es>
 */
public class Transaction {

    public int first;
    public List<Integer> second;

    /**
     * Class constructor
     */
    public Transaction() {
        this.first = 0;
        this.second = new ArrayList();
    }

    /**
     * Clear transaction
     */
    void clear() {
        this.first = 0;
        this.second = new ArrayList();
    }

}
