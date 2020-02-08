package com.example.booksapi.recherche;

import com.example.booksapi.grep.myGrep;
import com.example.booksapi.precalcul.index.Indexing;
import com.example.booksapi.precalcul.titres.titre;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Search
 */
public class Search {

    public static void main(String[] args) {
        String rechercheString = args[0];

        System.out.println("Sugestion : " + myGrep.regex(rechercheString));
    }

    public static ArrayList<HashMap<String, String>> search(String val) {
        ArrayList<HashMap<String, String>> books = new ArrayList<>();

        String rechercheString = val;
        ArrayList<String> paths = new ArrayList<>();
        HashMap<String, String> titres = titre.loadTitres();
        try {
            Files.walk(Paths.get("./data/index/")).filter(Files::isRegularFile).map(p -> p.toString())
                    .filter(p -> p.endsWith("index")).collect(Collectors.toList()).forEach(x -> paths.add(x));

            for (String path : paths) {
                HashMap<String, Integer> indexMap = Indexing.loadIndexMap(path);
                if (indexMap.containsKey(rechercheString)) {

                    String textString = path.substring(0, path.indexOf("/index"));
                    textString += "/books/" + path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
                    HashMap<String, String> h = new HashMap();
                    books.add(h);
                    h.put("title", titres.getOrDefault(textString, textString));
                    h.put("path", textString);
                }
                if (books.size() > 20) {
                    break;
                }
            }

        } catch (IOException io) {
            io.printStackTrace();
        }

        return books;
    }

    public static ArrayList<String> suggest(ArrayList<HashMap<String, String>> search_result) {

        ArrayList<String> suggestion = new ArrayList<>();

        try {
            try (Stream<Path> s = Files.walk(Paths.get("./data/cent/cent.cent"))) {
                s.flatMap(path -> {
                    try {
                        return Files.lines(path);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }).forEach(string -> {
                    // System.out.println(string + " *");

                    if (suggestion.size() <= 10) {

                        for (HashMap<String, String> hashMap : search_result) {

                            String path = hashMap.get("path");

                            path = path.substring(path.lastIndexOf("/") + 1);
                            // System.out.println(path + " ?");

                            try (Stream<Path> str = Files.walk(Paths.get("./data/voisins/" + path + ".index"))) {
                                ArrayList<String> voisins = str.flatMap(pth -> {
                                    try {
                                        return Files.lines(pth);
                                    } catch (IOException e) {
                                        throw new UncheckedIOException(e);
                                    }
                                }).collect(Collectors.toCollection(ArrayList::new));

                                // System.out.println(voisins + " #");

                                if (voisins.contains(string) && !suggestion.contains(hashMap.get("title"))) {
                                    suggestion.add(hashMap.get("title"));
                                    break;
                                }
                                // Thread.sleep(1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suggestion;
    }

}