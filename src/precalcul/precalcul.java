package precalcul;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

import Online.Centrality;
import Online.Display;
import Online.Graph;
import Online.HeapSort;
import myGrep.Positions;
import precalcul.index.Indexing;

public class precalcul {
    public static void main(String[] args) {
        System.out.println("\n######Starting nedded indexing######\n");
        Indexing.indexFiles();
        System.out.println("\n######Indexing Done######\n");

        System.out.println("\n######Starting documents load######\n");

        // ArrayList<Document> docs = new ArrayList<>();

        ArrayList<String> paths = new ArrayList<>();
        Graph g = null;

        try {
            Files.walk(Paths.get("./data/index/")).filter(Files::isRegularFile).map(p -> p.toString())
                    .filter(p -> p.endsWith("index")).collect(Collectors.toList()).forEach(x -> paths.add(x));

            System.out.println("\n######Load Done######\n");

            System.out.println("\n######Loading Graph######\n");


            if (!new File("./data/graphe/graphe.txt").isFile()) {
                g = new Graph(paths);
                Display.registerEdgesInFile("./data/graphe/graphe.txt", g);
            } else {
                g = new Graph(new ArrayList<>(Files.readAllLines(Paths.get("./data/graphe/graphe.txt"))), 0);
            }

        } catch (IOException io) {
            io.printStackTrace();
        }

        System.out.println("\n###### new Centrality ######\n");

        // affichage par ordre décroissant
        Centrality.printCent("./data/cent/cent.cent", Centrality.calcCentrality(g),paths);
        

        // System.out.println("\n###### Calculating POS ######\n");
        // Positions.posFiles();
    }
}