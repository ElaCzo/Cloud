package precalcul.centrality;

public class Closeness {

    /* Renvoie la closeness centrality d'un point d'indice i */
    private static double computeJaccard(int i, double[][] shortestPaths) {
        double sum = 0.0;
        for (int j = 0; j < shortestPaths[i].length; j++)
            if (i != j)
                sum += shortestPaths[i][j];

        if (sum == 0.0)
            return 0.0;
        else
            return ((double) (shortestPaths[i].length - 1.0)) / sum;
    }

    /*
     * Renvoie la liste des indices des sommets triés par indice de Closeness
     * croissants
     */
    public static double[] closeness(Graph g) {
        double[] closeness = new double[g.nbS];

        double shortestPaths[][] = Centrality.calculShortestPaths(g);

        for (int i = 0; i < closeness.length; i++)
            closeness[i] = computeJaccard(i, shortestPaths);

        return closeness;
    }
}
