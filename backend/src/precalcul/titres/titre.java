package precalcul.titres;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * titre
 */
public class titre {

    public static HashMap loadTitres(){
        HashMap<String, String> titres = new HashMap<>();

        try {

            Stream<String> stream = Files.lines(Paths.get("./data/titres/titres.ti"));

            for (String line : stream.collect(Collectors.toList())) {
                String[] columns = line.split("#");
                // System.out.println(columns.);
                String mot = columns[0];
                String path = columns[1];

                // System.out.println("|"+columns[0] +"| -> |" +columns[1]+"|");


                titres.put(path, mot);
            }
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return titres;
    }
}