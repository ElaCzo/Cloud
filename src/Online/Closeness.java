package Online;

import java.util.HashMap;
import java.util.HashSet;

public class Closeness {

    private static double sumJaccardfrom(
            int i,
            HashMap<Integer, HashSet<DistanceJaccard<Integer>>> jacquard){
        double sum = jacquard
                .get(i)
                .stream()
                .map(e -> e.getJacquard())
                .reduce(0.0, Double::sum);
        return sum;
    }

    public static void closeness(Graph g){
        HashMap<Integer, HashSet<DistanceJaccard<Integer>>> jaccard = g.getJaccard();
        Double[] sumJaccard = new Double[g.nbS];

        double sum;
        for(int i=0; i<g.nbS; i++) {
            sumJaccard[i]=sumJaccardfrom(i, jaccard);
        }
    }
}
