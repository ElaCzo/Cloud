package Online;

import java.util.ArrayList;

public class Closeness {

    /* Renvoie la closeness centrality d'un point d'indice i */
    private static double sumJaccardfrom(int i, ArrayList<ArrayList<Double>> jacquard){
        return jacquard
                .get(i)
                .stream()
                .reduce(0.0, Double::sum);
    }

    /* Renvoie la liste des indices des sommets triés par indice de Closeness
    croissants */
    public static int[] closeness(Graph g){
        ArrayList<ArrayList<Double>> jaccard = g.getJaccard();
        double [] closeness = new double[g.nbS];

        double sum=0;
        for(int i=0; i<g.nbS; i++) {
            sum = sumJaccardfrom(i, jaccard);
            if(sum>0)
                closeness[i]=sum;
            else
                closeness[i]=0;
        }

        // Comparer entre heapsort et stream().sorted() pour garder le plus efficace.
        int docsSorted[] = HeapSort.heapsort(closeness);

        return docsSorted;
    }
}
