package myGrep;

import java.util.ArrayList;

public class IndexTree {

    public ArrayList<Character> transitions;
    public ArrayList<IndexTree> fils;
    public ArrayList<TextPosition> feuilles;

    public IndexTree() {
        this.transitions = new ArrayList<>();
        this.fils = new ArrayList<>();
        this.feuilles = new ArrayList<>();
    }

    public void inserer(String valeur, ArrayList<TextPosition> positions) {
        if (valeur.isEmpty() ) {
            feuilles.addAll(positions);
            return;
        } else {

            char radix = valeur.charAt(0);

            for (int i = 0; i < transitions.size(); i++) {
                if (transitions.get(i) == radix) {
                    fils.get(i).inserer(valeur.substring(1), positions);
                    return;
                }
            }

            transitions.add(radix);
            fils.add(new IndexTree());
            fils.get(fils.size() - 1).inserer(valeur.substring(1), positions);

        }
    }

    public ArrayList<TextPosition> getPositions(String valeur) {

        ArrayList<TextPosition> retour = new ArrayList<>();

        if (valeur.isEmpty() ) {
            retour.addAll(feuilles);
            for (int i = 0; i < transitions.size(); i++) {
                retour.addAll(fils.get(i).getPositions(valeur));

            }
            return retour;
        }

        char radix = valeur.charAt(0);

        for (int i = 0; i < transitions.size(); i++) {
            if (transitions.get(i) == radix) {
                retour.addAll(fils.get(i).getPositions(valeur.substring(1)));
            }
        }

        return retour;

    }

}