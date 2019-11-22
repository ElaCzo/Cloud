package Online;

import java.util.ArrayList;

import Online.Graph;

public class Rank {

    public static ArrayList<Double> prodMatVec(ArrayList<ArrayList<Double>> mat, ArrayList<Double> vec) {
        ArrayList<Double> sortie = new ArrayList(vec.size());

        for (int i = 0; i < vec.size(); i++) {
            sortie.add(0.0);
            for (int j = 0; j < vec.size(); j++) {
                double degu = mat.get(i).get(j);
                sortie.set(i, sortie.get(i) + degu * vec.get(i));
            }
        }

        return sortie;
    }

    public static ArrayList<ArrayList<Double>> transitionmatrix(Graph g) {
        ArrayList<ArrayList<Double>> sortie = new ArrayList(g.nbS);
        for (int i = 0; i < g.nbS; i++) {
            sortie.add(new ArrayList<>(g.nbS));
            for (int j = 0; j < g.nbS; j++) {
                sortie.get(i).add(0.0);
            }
        }

        for (int i = 0; i < g.nbS; i++) {
            for (int j = 0; j < g.nbS; j++) {
                if (g.adjacence.get(i).contains(j)) {
                    double degu = g.adjacence.get(i).size();
                    if (degu == 0.0) {
                        sortie.get(i).set(j, 1.0 / g.nbS);
                    } else {
                        sortie.get(i).set(j, 1.0 / degu);

                    }
                }
            }
        }

        // System.out.println("######Transition Matrix#######");
        // for (int i = 0; i < g.nbS; i++) {
        // for (int j = 0; j < g.nbS; j++) {
        // System.out.print(sortie.get(i).get(j)+" ");
        // }
        // System.out.print("\n");

        // }

        return sortie;

    }

    public static ArrayList<Double> pageRank(Graph g, double alpha) {

        ArrayList<ArrayList<Double>> t = transitionmatrix(g);
        ArrayList<Double> sortie = new ArrayList<>(g.nbS);
        double entry = 1.0 / g.nbS;
        for (int i = 0; i < g.nbS; i++) {
            sortie.add(entry);
        }

        boolean cond = true;
        int round = 1;

        while (cond) {
            cond = false;
            System.out.println("** PageRank round : " + round + " **");
            ArrayList<Double> nsortie = prodMatVec(t, sortie);
            for (int i = 0; i < g.nbS; i++) {

                nsortie.set(i, (1.0 - alpha) * ((sortie.get(i) + alpha)) * entry);

                if (Math.abs(nsortie.get(i) - sortie.get(i)) > 0.0) {
                    cond = true;
                }
            }
            if (cond) {
                sortie = nsortie;
            }

            round += 1;
        }

        return sortie;

    }

}