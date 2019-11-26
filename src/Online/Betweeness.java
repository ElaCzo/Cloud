package Online;

import java.awt.Point;
import java.util.ArrayList;

public class Betweeness {

    public static int[] CalculateBetweeness(Graph g) {

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
                    dist[i][j]=g.getJaccard().get(i).get(g.adjacence.indexOf(j));
                else
                    dist[i][j] = Double.POSITIVE_INFINITY;
                paths[i][j] = j;
            }
        }

        double[] between = new double[g.nbS];
        for (int i = 0; i < between.length; i++) {
            between[i] = g.adjacence.get(i).size();
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

        

        for (int k = 0; k < between.length; k++) {
            for (int i = 0; i < paths.length; i++) {
                if (dist[k][i] != Double.POSITIVE_INFINITY) {

                    int j = paths[k][i];
                    while (j != i) {
                        between[j] += 1;
                        j = paths[j][i];
                    }

                }

            }

        }

        double max = 0;

        for (int i = 0; i < between.length; i++) {
            if (between[i] > max) {
                max = between[i];
            }
        }

        for (int i = 0; i < between.length; i++) {
            between[i] = between[i] / max;
        }

        int docsSorted[] = HeapSort.heapsort(between);

        return docsSorted;
    }
}