package myGrep;

import java.util.ArrayList;

/**
 * KMP
 */
public class KMP {

    public static ArrayList<TextPosition> kmp(ArrayList<String> text, char[] factor) {

        ArrayList<TextPosition> retour = new ArrayList<>();

        int ligne = 0;
        int i = 0;
        int[] reste = retenue(factor);

        for (String t : text) {

            for (char c : t.toCharArray()) {
                int pos = 0;

                if (c == factor[i]) {
                    i++;
                    if (i == factor.length) {
                        retour.add(new TextPosition(ligne, pos - factor.length));
                        i = reste[i];
                        if (i == -1) {
                            i = 0;
                        }
                    }
                } else {
                    i = reste[i];
                    if (i == -1) {
                        i = 0;
                    }
                }
                pos++;
            }
            ligne++;
        }

        return retour;
    }

    public static int[] retenue(char[] factor) {
        int[] ret = new int[factor.length + 1];

        int len = 0;
        int i = 1;
        ret[0] = 0;

        while (i < factor.length) {
            if (factor[i] == factor[len]) {
                len++;
                ret[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = ret[len - 1];

                } else {
                    ret[i] = len;
                    i++;
                }
            }

        }

        return ret;

    }

}