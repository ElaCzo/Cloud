package com.example.booksapi.grep;

import com.example.booksapi.precalcul.titres.titre;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class myGrep {

    public static ArrayList<HashMap<String, String>> regex(String regEx) {
        ArrayList<HashMap<String, String>> books = new ArrayList<>();

        ArrayList<String> paths = new ArrayList<>();
        HashMap<String, String> titres = titre.loadTitres();

        try {

            Files.walk(Paths.get("./data/books/")).filter(Files::isRegularFile).map(p -> p.toString())
                    .collect(Collectors.toList()).forEach(x -> paths.add(x));


            if (kmpable(regEx)) {


                for (String path : paths) {
                    ArrayList<String> text = new ArrayList<>(Files.readAllLines(Paths.get(path)));

                    ArrayList<TextPosition> pos = KMP.kmp(text, regEx.toCharArray());

                    if (pos.size() != 0) {
                        HashMap<String, String> h = new HashMap();
                        books.add(h);
                        h.put("title", titres.getOrDefault(path, path));
                        h.put("path", path);
                    }
                    if (books.size() > 50) {
                        break;
                    }

                }
            }

            if (!kmpable(regEx)) {

                try {

                    for (String path : paths) {
                        ArrayList<String> text = new ArrayList<>(Files.readAllLines(Paths.get(path)));

                        ArrayList<TextPosition> pos = Automate.getOccurencesOnText(text, regEx);

                        if (pos.size() != 0) {
                            HashMap<String, String> h = new HashMap();
                            books.add(h);
                            h.put("title", titres.getOrDefault(path, path));
                            h.put("path", path);
                        }
                        if (books.size() > 50) {
                            break;
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException io) {
            io.printStackTrace();
        }

        return books;

    }

    public static boolean kmpable(String facteur) {

        for (char c : facteur.toCharArray()) {
            if (c == '*' || c == '(' || c == ')' || c == '|') {
                return false;
            }
        }

        return true;

    }
}