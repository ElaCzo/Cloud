package Online;

import java.util.ArrayList;
import java.util.HashMap;

import Online.Indexing;

/**
 * Document
 */
public class Document {

    public HashMap<String, Integer> indexMap;

    public Document(String path) {

        this.indexMap = Indexing.loadIndexMap(path);

    }

}