package precalcul.centrality;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
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

    public static void printCent(String fichier, double[] cent, ArrayList<String> paths) {

        // affichage par ordre décroissant
        int[] scent = HeapSort.heapsort(cent);

        File f = new File(fichier);
        Writer writer = null;

        try {
            // ouverture d'un flux de sortie sur un fichier
            writer = new FileWriter(f);

            // création d'un PrintWriter sur ce flux
            PrintWriter pw = new PrintWriter(writer);

            for (int i = paths.size() - 1; i >= 0; i--) {

                String path = paths.get(scent[i]);
                path = path.substring(path.lastIndexOf('/') + 1, path.lastIndexOf('i') );

                pw.println(path);   

                pw.flush();
            }

            pw.close();
            writer.close();

        } catch (IOException e) {
            System.out.println("Problème écriture dans fichier cent .");
        } finally {
        }

    }

}