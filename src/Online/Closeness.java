package Online;

import java.util.HashMap;
import java.util.HashSet;

public class Closeness {

    /* Renvoie la closeness centrality d'un point d'indice i */
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

    /* Renvoie la liste des indices des sommets triés par indice de Closeness
    croissants */
    public static int[] closeness(Graph g){
        HashMap<Integer, HashSet<EdgeJaccard<Integer>>> jaccard = g.getJaccard();
        double[] closeness = new double[g.nbS];

        for(int i=0; i<g.nbS; i++)
            closeness[i] = 1.0/sumJaccardfrom(i, jaccard);

        // Comparer entre heapsort et stream().sorted() pour garder le plus efficace.
        int docsSorted[] = HeapSort.heapsort(closeness);

        return docsSorted;
    }
}
