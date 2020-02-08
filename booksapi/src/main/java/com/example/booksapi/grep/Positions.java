package com.example.booksapi.grep;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Positions {


    public static Boolean posFiles() {


        try (Stream<Path> paths = Files.walk(Paths.get("./data/books/"))) {
            paths.parallel().filter(Files::isRegularFile).map(p -> p.toString()).forEach(Positions::createPos);
        } catch (IOException io) {
            io.printStackTrace();
        }

        return true;

    }


    public static String createPos(String path) {
        String indexPath = path.substring(0, path.lastIndexOf('.'));
        String posPath = indexPath;
        posPath = path.substring(path.lastIndexOf('/') + 1, indexPath.length());
        indexPath = "./data/index/" + posPath + ".index";
        posPath = "./data/pos/" + posPath + ".pos";


        File f = new File(posPath);
        if (!f.isFile()) {


            try {
                ArrayList<String> txt = null;
                txt = new ArrayList<>(Files.readAllLines(Paths.get(indexPath)));
                ArrayList<String> index = new ArrayList<>();

                ArrayList<String> text = null;
                text = new ArrayList<>(Files.readAllLines(Paths.get(path)));


                for (String line : txt) {
                    String[] columns = line.split("\\s+");

                    String s = columns[0];
                    ArrayList<TextPosition> occ = KMP.kmp(text, s.toCharArray());

                    for (TextPosition tp : occ) {
                        s += " (" + tp.ligne + "," + tp.pos + ") ";
                    }

                    index.add(s);
                }


                Path file = Paths.get(posPath);
                Files.write(file, index, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return posPath;

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

            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return indexTree;
    }

}
