package precalcul;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import precalcul.centrality.Centrality;
import precalcul.centrality.Display;
import precalcul.centrality.Graph;
import precalcul.centrality.Voisins;
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
                g = new Graph(paths, new ArrayList<>(Files.readAllLines(Paths.get("./data/graphe/graphe.txt"))));
            }

        } catch (IOException io) {
            io.printStackTrace();
        }

        System.out.println("\n###### new Centrality ######\n");

        // affichage par ordre décroissant
        Centrality.printCent("./data/cent/cent.cent", Centrality.calcCentrality(g), paths);

        // System.out.println("\n###### Calculating POS ######\n");
        // Positions.posFiles();

        System.out.println("\n###### Voisins ######\n");
        Voisins.voisins(g);

        System.out.println("\n###### Titre ######\n");
        printTitre("./data/titres/titres.ti");

    }

    public static ArrayList<String> titres() {
        ArrayList<String> titres = new ArrayList<>();
        ArrayList<String> paths = new ArrayList<>();

        try {
            Files.walk(Paths.get("./data/books/")).filter(Files::isRegularFile).map(p -> p.toString())
                    .collect(Collectors.toList()).forEach(x -> paths.add(x));

            for (String path : paths) {

                List<String> lignes = Files.readAllLines(Paths.get(path));

                for (String ligne : lignes) {

                    if (ligne.startsWith("Title")) {
                        int index = ligne.indexOf('e', 0) + 1;
                        System.out.println(ligne + " ");
                        if (index > 0) {

                            ligne = ligne.substring(index + 1);
                            titres.add(ligne + "#" + path);
                        }

                    }
                }

            }

        } catch (IOException io) {
            io.printStackTrace();
        }

        return titres;
    }

    public static void printTitre(String fichier) {

        // affichage par ordre décroissant

        File f = new File(fichier);
        Writer writer = null;

        try {
            // ouverture d'un flux de sortie sur un fichier
            writer = new FileWriter(f);

            // création d'un PrintWriter sur ce flux
            PrintWriter pw = new PrintWriter(writer);

            for (String ligne : titres()) {

                pw.println(ligne);
            }

            pw.flush();

            pw.close();
            writer.close();

        } catch (IOException e) {
            System.out.println("Problème écriture dans fichier titres .");
        } finally {

        }

    }
}