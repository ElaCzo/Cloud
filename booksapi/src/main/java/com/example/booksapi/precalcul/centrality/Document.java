package com.example.booksapi.precalcul.centrality;

import com.example.booksapi.precalcul.index.Indexing;

import java.util.HashMap;

/**
 * Document
 */
public class Document {

    public HashMap<String, Integer> indexMap;

    public Document(String path) {

        this.indexMap = Indexing.loadIndexMap(path);

    }

}