package recherche;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import precalcul.index.Indexing;
import precalcul.titres.titre;

/**
 * Search
 */
public class Search {

    public static void main(String[] args) {
        String rechercheString = args[0];
        ArrayList<String> paths = new ArrayList<>();
        HashMap<String, String> titres = titre.loadTitres();
        try {

            Files.walk(Paths.get("./data/index/")).filter(Files::isRegularFile).map(p -> p.toString())
                    .filter(p -> p.endsWith("index")).collect(Collectors.toList()).forEach(x -> paths.add(x));

            for (String path : paths) {
                // System.out.println("test");
                HashMap<String, Integer> indexMap = Indexing.loadIndexMap(path);
                if (indexMap.containsKey(rechercheString)) {

                    String textString = path.substring(0, path.indexOf("/index"));
                    textString+="/books/"+path.substring(path.lastIndexOf("/")+1 , path.lastIndexOf("."));
                    System.out.println("\nFILENAME : "+textString);
                    System.out.println("NAME : " + titres.getOrDefault(textString, textString));

                }

            }

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static void test(String val){
        return val;
    }
}