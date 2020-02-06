package com.example.booksapi;

import recherche.Search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchBooksResult {
    private final long id;
    private final String search;
    private final List<Book> books;

    public SearchBooksResult(long id, String search) {
        this.id = id;
        this.search = search;
        this.books = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getSearch() { return search; }

    public List<Book> getBooks(){

        ArrayList<HashMap<String, String>> res = Search.search(search);

        for(HashMap<String, String> infosLivre : res){
            Book book = new Book();
            book.setId(infosLivre.get("path"));
            book.setPath(infosLivre.get("path"));
            book.setTitle(infosLivre.get("title"));
            books.add(book);
        }

        return books;
    }
}