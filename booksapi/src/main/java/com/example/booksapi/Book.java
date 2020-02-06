package com.example.booksapi;

public class Book {
    private String id;
    private String title;
    private String path;

    public Book() {
        this.id = "";
        this.title = "";
        this.path = "";
    }

    public Book(String id, String title, String path) {
        this.id = id;
        this.title = title;
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
