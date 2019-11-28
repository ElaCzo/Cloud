package Online;

public class Closeness {

    public static double[][] calculShortestPaths(Graph g) {
        int[][] paths = new int[g.nbS][g.nbS];
        for (int i = 0; i < paths.length; i++)
            for (int j = 0; j < paths.length; j++)
                paths[i][j] = i;

        double[][] dist=new double[g.nbS][g.nbS];

        for (int i=0;i<paths.length;i++) {
            for (int j=0;j<paths.length;j++) {
                if (i==j) {
                    dist[i][i]=0;
                    continue;
                }
                if (g.adjacence.get(i).contains(j)) {
                    dist[i][j] = g.getJaccard().get(i).get(g.adjacence.get(i).indexOf(j));
                }
                else
                    dist[i][j]=Double.POSITIVE_INFINITY;
                paths[i][j]=j;
            }
        }

        for (int k=0;k<paths.length;k++) {
            for (int i=0;i<paths.length;i++) {
                for (int j=0;j<paths.length;j++) {
                    if (dist[i][j]>dist[i][k] + dist[k][j]){
                        dist[i][j]=dist[i][k] + dist[k][j];
                        paths[i][j]=paths[i][k];

                    }
                }
            }
        }

        return dist;
    }

    /* Renvoie la closeness centrality d'un point d'indice i */
    private static double computeJaccard(int i, double[][] shortestPaths){
        double sum=0.0;
        for(int j=0; j<shortestPaths[i].length; j++)
            if(i!=j)
                sum+=shortestPaths[i][j];

        if(sum==0.0)
            return 0.0;
        else
            return 1.0/sum;
    }

    /* Renvoie la liste des indices des sommets triés par indice de Closeness
    croissants */
    public static int[] closeness(Graph g){
        double[] closeness = new double[g.nbS];

        double shortestPaths[][] =  calculShortestPaths(g);

        for(int i=0; i<closeness.length; i++)
            closeness[i]=computeJaccard(i, shortestPaths);

        // Comparer entre heapsort et stream().sorted() pour garder le plus efficace.
        int docsSorted[] = HeapSort.heapsort(closeness);

        return docsSorted;
    }
}
