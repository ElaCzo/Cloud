package Online;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// trier le moins fréquent par le plus fréquent.
// idée : black list (commune)
// - 2 char
// - fréquent caractère

// en black liste : tous les mots avec 2 car
// caractère fréquent : (a, etc) : articl indéfini.
// tou sles mots sup à une certaines fréquence : check par un humain.
public class Indexing {

    public static Boolean indexFiles() {

        try (Stream<Path> paths = Files.walk(Paths.get("./text/"))) {
            paths.filter(Files::isRegularFile).map(p -> p.toString()).forEach(Indexing::createIndex);
        } catch (IOException io) {
            io.printStackTrace();
        }
        return true;
    }

    public static String createIndex(String path) {
        ArrayList<String> text = new ArrayList<>();
        try {
            text = new ArrayList<>(Files.readAllLines(Paths.get(path)));

        } catch (IOException e) {
            e.printStackTrace();
        }

        String indexPath = path.substring(0, path.lastIndexOf('.'));
        indexPath = path.substring(path.lastIndexOf('/') + 1, indexPath.length());
        String textPath = indexPath;

        textPath = "./index/" + textPath + ".txt";
        indexPath = "./index/" + indexPath + ".index";

        File f = new File(indexPath);
        if (!f.isFile()) {
            System.out.println("Start indexing : " + textPath);
            try {
                Runtime.getRuntime().exec("./script.bash " + path + " " + textPath).waitFor();

            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Etape 2 indexing  : " + textPath);

            try {
                ArrayList<String> txt = null;
                txt = new ArrayList<>(Files.readAllLines(Paths.get(textPath), StandardCharsets.ISO_8859_1));
                ArrayList<String> index = new ArrayList<>();
                int cpt = 0;
                int size = txt.size();

                System.out.print("Etape 3 indexing  : " + textPath + " #### " + cpt + " / " + size + "\r");

                for (String line : txt) {
                    String[] columns = line.split("\\s+");

                    if (columns.length > 2 ) {

                        String s = columns[2];
                        ArrayList<TextPosition> occ = KMP.kmp(text, s.toCharArray());

                        for (TextPosition tp : occ) {
                            s += " (" + tp.ligne + "," + tp.pos + ") ";
                        }

                        index.add(s);

                    } else {
                        break;
                    }

                    System.out.print("Etape 3 indexing  : " + textPath + " #### " + cpt + " / " + size + "\r");

                    cpt++;
                }

                Path file = Paths.get(indexPath);
                Files.write(file, index, StandardCharsets.UTF_8);
                System.out.print("Etape 3 indexing  : " + textPath + " DONE!                        \n\n");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return indexPath;

    }

    public static IndexTree loadIndexTree(String indexPath) {
        IndexTree indexTree = new IndexTree();

        try {
            Stream<String> stream = Files.lines(Paths.get(indexPath));

            for (String line : stream.collect(Collectors.toList())) {
                String[] columns = line.split("\\s+");
                String mot = columns[0];
                ArrayList<TextPosition> pos = new ArrayList<>();

                for (int i = 1; i < columns.length; i++) {
                    String[] value = columns[i].split("[(,)]");
                    pos.add(new TextPosition(Integer.parseInt(value[1]), Integer.parseInt(value[2])));
                }

                indexTree.inserer(mot, pos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return indexTree;
    }

    public static HashMap loadIndexMap(String indexPath) {
        HashMap<String, Integer> indexmap = new HashMap<>();

        try {
            Stream<String> stream = Files.lines(Paths.get(indexPath));

            for (String line : stream.collect(Collectors.toList())) {
                String[] columns = line.split("\\s+");
                String mot = columns[0];
                ArrayList<TextPosition> pos = new ArrayList<>();

                for (int i = 1; i < columns.length; i++) {
                    String[] value = columns[i].split("[(,)]");
                    pos.add(new TextPosition(Integer.parseInt(value[1]), Integer.parseInt(value[2])));
                }

                indexmap.put(mot, pos.size());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return indexmap;
    }

    public static ArrayList loadIndexArray(String indexPath) {
        ArrayList<String> indexArray = new ArrayList<>();

        try {
            Stream<String> stream = Files.lines(Paths.get(indexPath));

            for (String line : stream.collect(Collectors.toList())) {
                String[] columns = line.split("\\s+");
                String mot = columns[0];

                indexArray.add(mot);
            }

            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return indexArray;
    }

}
