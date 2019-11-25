package Online;

import java.util.ArrayList;
import java.util.HashMap;

import Online.IndexTree;
import Online.Indexing;

/**
 * Document
 */
public class Document {

    public String nom;

    public ArrayList<String> indexArray;
    public HashMap<String, Integer> indexMap;

    public String indexPath;
    public String textPath;

    public ArrayList<String> Texte;

    public Document(String path) {

        String indexPath = path.substring(0, path.lastIndexOf('.'));
        this.nom = path.substring(path.lastIndexOf('/') + 1, indexPath.length());

        this.textPath = "./text/" + nom + ".txt";
        this.indexPath = "./index/" + nom + ".index";

        this.indexArray = Indexing.loadIndexArray(this.indexPath);
        this.indexMap = Indexing.loadIndexMap(this.indexPath);

    }

}