package Online;

import java.util.ArrayList;

/**
 * Centrality
 */
public class Centrality {

    public static double[][] calculShortestPaths(Graph g) {
        int[][] paths = new int[g.nbS][g.nbS];
        for (int i = 0; i < paths.length; i++)
            for (int j = 0; j < paths.length; j++)
                paths[i][j] = i;

        double[][] dist = new double[g.nbS][g.nbS];

        for (int i = 0; i < paths.length; i++) {
            for (int j = 0; j < paths.length; j++) {
                if (i == j) {
                    dist[i][i] = 0;
                    continue;
                }
                if (g.adjacence.get(i).contains(j)) {
                    dist[i][j] = g.getJaccard().get(i).get(g.adjacence.get(i).indexOf(j));
                } else
                    dist[i][j] = Double.POSITIVE_INFINITY;
                paths[i][j] = j;
            }
        }

        for (int k = 0; k < paths.length; k++) {
            for (int i = 0; i < paths.length; i++) {
                for (int j = 0; j < paths.length; j++) {
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        paths[i][j] = paths[i][k];

                    }
                }
            }
        }

        return dist;
    }

    public static double[] calcCentrality(Graph g) {

        double[] cl = Closeness.closeness(g);
        double[] bw = Betweeness.CalculateBetweeness(g);
        double[] cent = new double[g.nbS];

        for (int i = 0; i < cent.length; i++)
            cent[i] = cl[i] + bw[i];

        return cent;
    }

}