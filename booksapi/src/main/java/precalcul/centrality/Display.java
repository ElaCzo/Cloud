package precalcul.centrality;

import java.io.*;
import java.util.ArrayList;

public class Display {
    public static void registerEdgesInFile(String fichier, Graph g){
        ArrayList<ArrayList<Integer>> adjacence = g.getAdjacence();
        ArrayList<ArrayList<Double>> jaccard = g.getJaccard();

        File f =  new File(fichier) ;
        Writer writer = null ;

        try {
            // ouverture d'un flux de sortie sur un fichier
            writer =  new FileWriter(f) ;

            // création d'un PrintWriter sur ce flux
            PrintWriter pw =  new PrintWriter(writer) ;

            int a = 0;
            // Impression des voisins dans un fichier.
            // Chaque ligne représente les voisins de l'indice = numéro de ligne.

            while(a < adjacence.size()) {
                ArrayList<Integer> voisins = adjacence.get(a);
                ArrayList<Double> jaccardVoisins = jaccard.get(a);

                for(int v=0; v<voisins.size(); v++) {
                    pw.print(voisins.get(v) + " " + jaccardVoisins.get(v) + " ");
                    pw.flush();
                }

                pw.println();
                pw.flush();

                a++;
            }
            pw.close();
            writer.close();

        }  catch (IOException e) {
            System.out.println("Problème écriture jaccard dans fichier.");
        }  finally {

        }

    }
}
