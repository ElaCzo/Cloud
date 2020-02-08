package precalcul.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Indexing {
    public static HashSet<String> filtre = null;

    public static Boolean indexFiles() {

        try {

            filtre = new HashSet<>(Files.readAllLines(Paths.get("./data/filtre/1000.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Stream<Path> paths = Files.walk(Paths.get("./data/books/"))) {
            paths.parallel().filter(Files::isRegularFile).map(p -> p.toString()).forEach(Indexing::createIndex);
        } catch (IOException io) {
            io.printStackTrace();
        }

        return true;

    }

    public static String createIndex(String path) {
        if (filtre == null) {
            try {

                filtre = new HashSet<>(Files.readAllLines(Paths.get("./data/filtre/1000.txt")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // String indexPath = path.substring(0, path.lastIndexOf('.'));
        String indexPath = path ;

        indexPath = path.substring(path.lastIndexOf('/') + 1, indexPath.length());

        indexPath = "./data/index/" + indexPath + ".index";

        File f = new File(indexPath);
        if (!f.isFile()) {

            System.out.println("Start indexing : " + path);
            try {

                Runtime.getRuntime().exec("./backend/script.bash " + path + " " + indexPath).waitFor();

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ArrayList<String> txt = null;
                txt = new ArrayList<>(Files.readAllLines(Paths.get(indexPath), StandardCharsets.UTF_8));
                ArrayList<String> index = new ArrayList<>();

                for (String line : txt) {

                    String[] columns = line.split("\\s+");

                    if (columns.length > 2) {

                        String s = columns[2];
                        if (!filtre.contains(s)) {
                            index.add(s + " " + columns[1]);
                        }

                    } else {
                        break;
                    }

                }

                Path file = Paths.get(indexPath);
                Files.write(file, index, StandardCharsets.UTF_8);
                System.out.print("Indexing  : " + path + " DONE!         \n\n");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return indexPath;

    }

    public static HashMap loadIndexMap(String indexPath) {
        HashMap<String, Integer> indexmap = new HashMap<>();

        try {
            Stream<String> stream = Files.lines(Paths.get(indexPath));

            for (String line : stream.collect(Collectors.toList())) {
                String[] columns = line.split("\\s+");
                String mot = columns[0];
                int occurences = Integer.parseInt(columns[1]);

                indexmap.put(mot, occurences);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return indexmap;
    }
}