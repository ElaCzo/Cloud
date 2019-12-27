package Online;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashMap;

/**
 * Graph
 */
public class Graph {

    public ArrayList<String> paths;

    // public ArrayList<Document> docs;

    public int nbS;
    public ArrayList<ArrayList<Integer>> adjacence;
    /*
     * A chaque indice du tableau correspond la distance de jaccard en fonction de
     * la liste d'adjacence :
     */
    protected ArrayList<ArrayList<Double>> jaccard;

    public Graph(ArrayList<String> paths) {
        this.paths = paths;
        nbS = 0;
        adjacence = new ArrayList<>();
        initJaccard();

        for (String s : paths) {
            addSommet();
        }

        HashMap<Integer, Document> docMap = new HashMap<>(paths.size());

        for (int i = 0; i < paths.size(); i++) {
            if (!docMap.containsKey(i)) {
                docMap.put(i, new Document(paths.get(i)));
            }
            System.out.println("i : " + i);
            for (int j = i + 1; j < paths.size(); j++) {
                // System.out.println("j : "+j);

                if (!docMap.containsKey(j)) {
                    docMap.put(j, new Document(paths.get(j)));
                }

                double dist = distJacquard(docMap.get(i), docMap.get(j));

                // System.out.println("distance entre:" + docs.get(i).nom + " et " +
                // docs.get(j).nom + " : " + dist);
                if (dist < 0.85) {
                    addEdge(i, j);
                    addJaccard(i, j, dist);
                }
            }

        }
    }

    public Graph(ArrayList<String> adj, int t) {

        this.paths = adj;
        nbS = 0;
        adjacence = new ArrayList<>();
        initJaccard();

        for (String s : paths) {
            addSommet();
        }

        int i = 0;

        for (String line : adj) {

            String[] columns = line.split("\\s+");

            for (int j = 0; j+1 < columns.length; j += 2) {

                if (columns[j]!= null && columns[j+1]!= null){

                    addEdge(i, Integer.parseInt(columns[j]));
                    addJaccard(i, Integer.parseInt(columns[j]), Double.parseDouble(columns[j + 1]));
                }

            }

        }
        i++;

    }

    private void initJaccard() {
        jaccard = new ArrayList<>();
        for (int i = 0; i < paths.size(); i++)
            jaccard.add(new ArrayList<>());
    }

    public void addSommet() {
        nbS++;
        adjacence.add(new ArrayList<>());

    }

    /* Ajoute à jaccard la distance de jaccard entre i et j */
    public void addJaccard(int i, int j, double dist) {
        for (int k = jaccard.get(i).size(); k < adjacence.get(i).size(); k++)
            jaccard.get(i).add(-1.0);
        for (int k = jaccard.get(j).size(); k < adjacence.get(j).size(); k++)
            jaccard.get(j).add(-1.0);

        jaccard.get(i).set(adjacence.get(i).indexOf(j), dist);
        jaccard.get(j).set(adjacence.get(j).indexOf(i), dist);
    }

    public void addEdge(int i, int j) {

        if (!adjacence.get(i).contains(j)) {
            adjacence.get(i).add(j);
            if (!adjacence.get(j).contains(i)) {
                adjacence.get(j).add(i);
            }
        }
    }

    public String noeudSeuls() {
        String sortie = "";
        for (int i = 0; i < this.nbS; i++) {
            if (this.adjacence.get(i).size() == 0) {
                sortie += i + "\n";
            }
        }
        return sortie;
    }

    public double distJacquard(Document d1, Document d2) {
        double somme = 0;
        double sommemax = 0;

        for (String motD1 : d1.indexMap.keySet()) {
            if (d2.indexMap.containsKey(motD1)) {
                int max = Math.max(d1.indexMap.get(motD1), d2.indexMap.get(motD1));
                int min = Math.min(d1.indexMap.get(motD1), d2.indexMap.get(motD1));

                somme += (max - min);
                sommemax += max;
            } else {
                somme += d1.indexMap.get(motD1);

                sommemax += d1.indexMap.get(motD1);
            }

        }
        for (String motD2 : d2.indexMap.keySet()) {
            if (!d1.indexMap.containsKey(motD2)) {
                somme += d2.indexMap.get(motD2);

                sommemax += d2.indexMap.get(motD2);
            }
        }

        return somme / sommemax;
    }

    /* GETTERS */
    public ArrayList<ArrayList<Double>> getJaccard() {
        return jaccard;
    }

    public ArrayList<ArrayList<Integer>> getAdjacence() {
        return adjacence;
    }
}