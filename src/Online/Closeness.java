package Online;

import java.util.HashMap;
import java.util.HashSet;

public class Closeness {

    private static double sumJaccardfrom(
            int i,
            HashMap<Integer, HashSet<EdgeJaccard<Integer>>> jacquard){
        double sum = jacquard
                .get(i)
                .stream()
                .map(e -> e.getJacquard())
                .reduce(0.0, Double::sum);
        return sum;
    }

    public static int[] closeness(Graph g){
        HashMap<Integer, HashSet<EdgeJaccard<Integer>>> jaccard = g.getJaccard();
        double[] closeness = new double[g.nbS];

        int docsSorted[] = new int[g.nbS];
        for(int i=0; i<g.nbS; i++) {
            closeness[i] = 1.0/sumJaccardfrom(i, jaccard);
            docsSorted[i]=i;
        }

        HeapSort.heapsort(closeness, docsSorted);

        return docsSorted;
    }
}
