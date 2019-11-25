package Online;

import java.awt.Point;
import java.util.ArrayList;

public class Betwenness {

    public static int[] CalculateBetwenness(Graph g) {

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

        double[] betwen = new double[g.nbS];
        for (int i = 0; i < betwen.length; i++) {
            betwen[i] = g.adjacence.get(i).size();
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

        

        for (int k = 0; k < betwen.length; k++) {
            for (int i = 0; i < paths.length; i++) {
                if (dist[k][i] != Double.POSITIVE_INFINITY) {

                    int j = paths[k][i];
                    while (j != i) {
                        betwen[j] += 1;
                        j = paths[j][i];
                    }

                }

            }

        }

        double max = 0;

        for (int i = 0; i < betwen.length; i++) {
            if (betwen[i] > max) {
                max = betwen[i];
            }
        }

        for (int i = 0; i < betwen.length; i++) {
            betwen[i] = betwen[i] / max;
        }

        int docsSorted[] = HeapSort.heapsort(betwen);

        return docsSorted;
    }
}