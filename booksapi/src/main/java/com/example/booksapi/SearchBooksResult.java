package com.example.booksapi;

import recherche.Search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchBooksResult {
    private final long id;
    private final String search;
    private final List<Book> books;
    private final List<Book> suggestions;

    public SearchBooksResult(long id, String search) {
        this.id = id;
        this.search = search;
        this.books = new ArrayList<>();
        this.suggestions = new ArrayList<>();

        ArrayList<HashMap<String, String>> res = Search.search(search);

        for(HashMap<String, String> infosLivre : res){
            Book book = new Book();
            book.setId(infosLivre.get("path"));
            book.setPath(infosLivre.get("path"));
            book.setTitle(infosLivre.get("title"));
            books.add(book);
        }

        ArrayList<String> sugg = Search.suggest(res);

        System.out.println("sugg : "+sugg.size());
        for(String suggi : sugg){
            Book book = new Book();
            book.setTitle(suggi);
            suggestions.add(book);
            System.out.println(book.getTitle());
        }
    }

    public long getId() {
        return id;
    }

    public String getSearch() { return search; }

    public List<Book> getBooks(){
        return books;
    }

    public List<Book> getSugg(){
        return suggestions;
    }
}