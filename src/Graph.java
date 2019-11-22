package Online;

import java.util.ArrayList;

/**
 * Graph
 */
public class Graph {

    public ArrayList<Documents> docs;
    public int nbS;
    public ArrayList<ArrayList<Integer>> adjacence;

    public Graph(ArrayList<Documents> docs) {
        this.docs = docs;
        nbS = 0;
        adjacence = new ArrayList<>();
    }

    public void addSommet() {
        nbS++;
        adjacence.add(new ArrayList<>());

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

    
}