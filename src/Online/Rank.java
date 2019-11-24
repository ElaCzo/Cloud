package Online;

import java.util.ArrayList;

import Online.Graph;

public class Rank {

    public static ArrayList<Double> prodMatVec(ArrayList<ArrayList<Double>> mat, ArrayList<Double> vec) {
        ArrayList<Double> sortie = new ArrayList(vec.size());

        for (int i = 0; i < vec.size(); i++) {
            sortie.add(0.0);
            for (int j = 0; j < vec.size(); j++) {
                sortie.set(i, sortie.get(i) + (mat.get(i).get(j) * vec.get(i)));
            }
        }

        // normalisation du vecteur
        double somme = 0;
        for (int i = 0; i < sortie.size(); i++) {
            somme += sortie.get(i);
        }
        // System.out.println("SOMME PRODMAT : " + somme);
        for (int i = 0; i < sortie.size(); i++) {
            sortie.set(i, sortie.get(i) / somme);
        }

        return sortie;
    }

    public static ArrayList<ArrayList<Double>> transitionmatrix(Graph g) {
        // initialisation de la matrice
        ArrayList<ArrayList<Double>> sortie = new ArrayList(g.nbS);
        for (int i = 0; i < g.nbS; i++) {
            sortie.add(new ArrayList<>(g.nbS));
            for (int j = 0; j < g.nbS; j++) {
                sortie.get(i).add(0.0);
            }
        }

        // assigantion des valeurs
        // pour toutes les arretes
        for (int i = 0; i < g.nbS; i++) {
            for (int j = 0; j < g.nbS; j++) {
                if (g.adjacence.get(i).contains(j)) {
                    double deg = g.adjacence.get(i).size();

                    if (deg == 0.0) {
                        sortie.get(i).set(j, 1.0 / g.nbS);
                    } else {
                        sortie.get(i).set(j, 1.0 / deg);

                    }
                }
            }
        }

        // normalisation des lignes
        for (int i = 0; i < g.nbS; i++) {
            double sommeligne = 0;
            for (int j = 0; j < g.nbS; j++) {
                sommeligne += sortie.get(i).get(j);
                
            }
            // System.out.println(sommeligne+"\n");
            if (sommeligne == 1||sommeligne==0) {
                continue;
            } else {
                for (int j = 0; j < g.nbS; j++) {
                    sortie.get(i).set(j, sortie.get(i).get(j) / sommeligne);
                }
            }
        }
        // normalisation des colonnes

        for (int j = 0; j < g.nbS; j++) {
            double sommecol = 0;
            for (int i = 0; i < g.nbS; i++) {
                sommecol += sortie.get(i).get(j);
            }
            if (sommecol == 1||sommecol==0) {
                continue;
            } else {
                for (int i = 0; i < g.nbS; i++) {
                    sortie.get(i).set(j, sortie.get(i).get(j) / sommecol);
                }
            }
        }
        // print de la matrice
        // System.out.println("######Transition Matrix#######");
        // for (int i = 0; i < g.nbS; i++) {
        // for (int j = 0; j < g.nbS; j++) {
        // System.out.print(sortie.get(i).get(j) + " ");
        // }
        // System.out.print("\n");

        // }

        return sortie;

    }

    public static ArrayList<Double> pageRank(Graph g, double alpha) {

        ArrayList<ArrayList<Double>> matrix = transitionmatrix(g);
        ArrayList<Double> sortie = new ArrayList<>(g.nbS);
        double entry = 1.0 / g.nbS;

        // initialisation du vecteur
        for (int i = 0; i < g.nbS; i++) {
            sortie.add(entry);
        }

        boolean cond = true;
        int round = 1;

        while (cond) {

            cond = false;
            // print
            // System.out.println("** PageRank round : " + round + " **");

            // for (int i = 0; i < g.nbS; i++) {
            // System.out.println("val : " + sortie.get(i));

            // }

            // produit matriciel
            ArrayList<Double> nsortie = prodMatVec(matrix, sortie);

            // application de la formule
            for (int i = 0; i < g.nbS; i++) {

                nsortie.set(i, (1.0 - alpha) * (nsortie.get(i) + alpha) * entry);
                // nsortie.set(i, nsortie.get(i) + (1.0 - sortie.get(i)) / g.nbS);

                if (Math.abs(nsortie.get(i) - sortie.get(i)) > 0.0000001) {
                    cond = true;
                }
            }

            if (cond) {
                sortie = nsortie;
            }

            round += 1;

        }
        // normalisation du vecteur
        double somme = 0;
        for (int i = 0; i < sortie.size(); i++) {
            somme += sortie.get(i);
        }
        // System.out.println("SOMME : " + somme);
        for (int i = 0; i < sortie.size(); i++) {
            sortie.set(i, sortie.get(i) / somme);
        }

        return sortie;

    }

}