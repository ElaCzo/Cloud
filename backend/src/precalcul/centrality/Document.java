package precalcul.centrality;

import java.util.ArrayList;
import java.util.HashMap;

import precalcul.index.Indexing;

/**
 * Document
 */
public class Document {

    public HashMap<String, Integer> indexMap;

    public Document(String path) {

        this.indexMap = Indexing.loadIndexMap(path);

    }

}