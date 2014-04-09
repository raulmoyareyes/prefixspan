
package prefixspan;

import prefixspan.algorithm.PrefixSpan;

/**
 *
 * @author Raúl Moya Reyes <raulmoya.es>
 * @author Agustín Ruiz Linares <agustruiz.es>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PrefixSpan a = new PrefixSpan(1, 0);
        a.run("src/prefixspan/instances/data-test");
    }
    
}
