package Online;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Online.Document;
import Online.Graph;
import Online.Indexing;
import Online.Rank;

/**
 * Online
 * 
 */
public class Online {
    public static void main(String[] args) {

        System.out.println("######Starting nedded indexing######\n");
        Indexing.indexFiles();
        System.out.println("\n######Indexing Done######");

        ArrayList<Document> docs = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get("./index/"))) {
            List<String> res = paths.filter(Files::isRegularFile).map(p -> p.toString())
                    .filter(p -> p.endsWith("index")).collect(Collectors.toList());

            res.forEach(x -> docs.add(new Document(x)));

        } catch (IOException io) {
            io.printStackTrace();
        }

        Graph g = new Graph(docs);
        System.out.println(g);


        System.out.println("\n###### Closeness ######\n");

        // affichage par ordre décroissant
        int[] closeness = Closeness.closeness(g);
        for (int i = docs.size()-1; i >=0; i--) {
            System.out.println("doc : " + docs.get(closeness[i]).nom);
        }

        // ArrayList<Double> pagerank = Rank.pageRank(g, 0.15);

        // System.out.println("\n######pageRank ######\n");

        // for (int i = 0; i < pagerank.size(); i++) {
        //     System.out.println("doc : " + docs.get(i).nom + " pageRank : " + pagerank.get(i));
        // }

    }

}