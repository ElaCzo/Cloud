package Online;

import java.awt.Point;
import java.util.ArrayList;

public class FloydMarshall {

    public static double[] calculShortestPaths(Graph g) {
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
                if (g.adjacence.get(i).contains(j))
                    dist[i][j] = 1;
                else
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

        double[] B = new double[g.nbS];
        for (int i = 0; i < B.length; i++) {
            B[i] = 0;
        }

        for (int k = 0; k < B.length; k++) {
            for (int i = 0; i < paths.length; i++) {
                if (dist[k][i] != Double.POSITIVE_INFINITY) {

                    int j = paths[k][i];
                    while (j != i) {
                        B[j] += 1;
                        j = paths[j][i];
                    }

                }

            }

        }

        double max = 0;

        for (int i = 0; i < B.length; i++) {
            if (B[i] > max) {
                max = B[i];
            }
        }

        for (int i = 0; i < B.length; i++) {
            B[i] = B[i] / max;
        }

        return B;
    }
}