package Online;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Closeness {
    public static void closeness(Graph g){
        HashMap<Integer, HashSet<EdgeJacquard<Integer>>> jacquard = g.getJacquard();
        Double[] sumJacquard = new Double[g.nbS];

        for(int i=0; i<g.nbS; i++) {
            /*double sum = jacquard
                    .get(i)
                    .map(e -> Math.min(e.getA(), e.getB()))
                    .reduce(0, (e1, e2) -> e1 + e2)
                    .doubleValue();*/
        }
    }
}
