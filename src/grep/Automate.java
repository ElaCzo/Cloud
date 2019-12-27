package myGrep;

import org.w3c.dom.Text;

import java.util.*;
import java.util.stream.Collectors;

public class Automate {
    int[][] states;
    boolean[] debut;
    boolean[] fin;
    ArrayList<Integer>[] epsilon;

    public Automate(int n) {
        this.states = new int[n][256];
        this.debut = new boolean[n];
        this.fin = new boolean[n];
        this.epsilon = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            epsilon[i] = new ArrayList<>();
            fin[i] = false;
            debut[i] = false;
            for (int j = 0; j < 256; j++) {
                this.states[i][j] = -1;
            }
        }
    }

    public Automate(char a) {
        this(2);
        debut[0] = true;
        fin[1] = true;
        states[0][(int) a] = 1;
    }

    private Automate(Automate a, int n) {
        this.states = new int[n][256];
        this.debut = new boolean[n];
        this.fin = new boolean[n];
        this.epsilon = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            epsilon[i] = a.epsilon[i];
            fin[i] = a.fin[i];
            debut[i] = a.debut[i];
            for (int j = 0; j < 256; j++) {
                this.states[i][j] = a.states[i][j];
            }
        }
    }

    public static Automate concat(Automate A1, Automate A2) {
        Automate sortie = new Automate(A1.nbStates() + A2.nbStates());

        for (int i = 0; i < A1.nbStates(); i++) {
            for (int j = 0; j < 256; j++) {
                if (A1.states[i][j] != -1) {
                    sortie.states[i][j] = A1.states[i][j];
                }
            }
            sortie.debut[i] = A1.debut[i];
            sortie.epsilon[i].addAll(A1.epsilon[i]);

            if (A1.fin[i] == true) {
                for (int k = 0; k < A2.nbStates(); k++) {
                    if (A2.debut[k] == true) {
                        sortie.epsilon[i].add(k + A1.nbStates());
                    }
                }
            }
        }

        for (int i = 0; i < A2.nbStates(); i++) {
            for (int j = 0; j < 256; j++) {
                if (A2.states[i][j] != -1) {

                    sortie.states[i + A1.nbStates()][j] = A2.states[i][j] + A1.nbStates();
                }
            }
            sortie.fin[i + A1.nbStates()] = A2.fin[i];
            for(int s : A2.epsilon[i])
                sortie.epsilon[i + A1.nbStates()].add(s+A1.nbStates());
        }

        return sortie;
    }

    public static Automate union(Automate A1, Automate A2) {
        Automate sortie = new Automate(A1.nbStates() + A2.nbStates() + 2);

        for (int i = 0; i < A1.nbStates(); i++) {
            for (int j = 0; j < 256; j++) {
                sortie.states[i][j] = A1.states[i][j];
            }
            sortie.epsilon[i].addAll(A1.epsilon[i]);

        }

        for (int i = 0; i < A2.nbStates(); i++) {
            for (int j = 0; j < 256; j++) {
                if (A2.states[i][j] != -1) {
                    sortie.states[i + A1.nbStates()][j] = A2.states[i][j] + A1.nbStates();
                }
            }
            for(int s : A2.epsilon[i])
                sortie.epsilon[i + A1.nbStates()].add(s+A1.nbStates());
        }

        sortie.debut[sortie.nbStates() - 2] = true;
        sortie.fin[sortie.nbStates() - 1] = true;

        for (int i = 0; i < A1.nbStates(); i++) {

            if (A1.debut[i] == true) {
                sortie.epsilon[sortie.nbStates() - 2].add(i);
            }

            if (A1.fin[i] == true) {
                sortie.epsilon[i].add(sortie.nbStates() - 1);
            }

        }

        for (int i = 0; i < A2.nbStates(); i++) {

            if (A2.debut[i] == true) {
                sortie.epsilon[sortie.nbStates() - 2].add(i + A1.nbStates());
            }

            if (A2.fin[i] == true) {
                sortie.epsilon[i + A1.nbStates()].add(sortie.nbStates() - 1);
            }
        }

        return sortie;
    }

    public static Automate closure(Automate a) {
        Automate sortie = new Automate(a.nbStates() + 2);

        for (int i = 0; i < a.nbStates(); i++) {
            for (int j = 0; j < 256; j++) {
                sortie.states[i][j] = a.states[i][j];
            }
            sortie.epsilon[i].addAll(a.epsilon[i]);
        }

        sortie.debut[sortie.nbStates() - 2] = true;
        sortie.fin[sortie.nbStates() - 1] = true;
        sortie.epsilon[sortie.nbStates() - 2].add(sortie.nbStates() - 1);

        for (int i = 0; i < a.nbStates(); i++) {

            if (a.debut[i] == true) {
                sortie.epsilon[sortie.nbStates() - 2].add(i);
            }

            if (a.fin[i] == true) {
                sortie.epsilon[i].add(sortie.nbStates() - 1);
                for (int j = 0; j < a.nbStates(); j++) {
                    if (a.debut[j] == true) {
                        sortie.epsilon[i].add(j);
                    }
                }
            }

        }

        return sortie;
    }

    public static Automate fromTree(RegExTree t) {

        if (t.root == RegEx.CONCAT)
            return concat(fromTree(t.subTrees.get(0)), fromTree(t.subTrees.get(1)));
        if (t.root == RegEx.ETOILE)
            return closure(fromTree(t.subTrees.get(0)));
        if (t.root == RegEx.ALTERN)
            return union(fromTree(t.subTrees.get(0)), fromTree(t.subTrees.get(1)));
        if (t.root == RegEx.DOT)
            return concat(fromTree(t.subTrees.get(0)), fromTree(t.subTrees.get(1)));
        else {
            return new Automate((char) t.root);
        }
    }

    private class SetOfStates extends HashSet<Integer>{}

    private SetOfStates statesReachingEpsilon(int state) {
        SetOfStates result = new SetOfStates();

        boolean marked[] = new boolean[nbStates()];
        for(int i=0; i<nbStates(); i++){
            marked[i]=false;
        }

        marked[state]=true;

        for(int e : epsilon[state]) {
            SetOfStates r = statesReachingEpsilon(e, marked);
            result.addAll(r);
        }

        return result;
    }

    private SetOfStates statesReachingEpsilon(int state, boolean marked[]){
        SetOfStates result = new SetOfStates();

        if(marked[state])
            return new SetOfStates();

        result.add(state);
        marked[state]=true;

        for(int e : epsilon[state]) {
            SetOfStates r = statesReachingEpsilon(e, marked);
            result.addAll(r);
        }

        return result;
    }

    private SetOfStates statesReachingLetter(int state, int letter){
        SetOfStates result = new SetOfStates();

        if(states[state][letter]!=-1) {
            result.add(states[state][letter]);
            result.addAll(statesReachingEpsilon(states[state][letter]));
        }

        return result;
    }

    /* Il s'agit d'une déterminisation dans un cas précis : un état pour avoir plusieurs epsilon transitions
    et celles-ci peuvent mener à une transition possédant la même lettre. */
    public Automate determinize() {
        Automate result = new Automate(nbStates());

        ArrayList<SetOfStates> statesNDAToDA = new ArrayList<>();

        ArrayList<SetOfStates> stack = new ArrayList<>();
        // On cherche le début
        for (int i = 0; i < nbStates(); i++) {
            SetOfStates statesNDA;
            if(debut[i]) {
                statesNDA = statesReachingEpsilon(i);
                statesNDA.add(i);
                stack.add(statesNDA);
                statesNDAToDA.add(statesNDA);
                result.debut[statesNDAToDA.indexOf(statesNDA)] = true;
                break;
            }
        }

        // tant que la pile n'est pas vide
        SetOfStates set;
        while(!stack.isEmpty()){
            set=stack.remove(stack.size()-1);
            for (int l = 0; l < 256; l++) {
                SetOfStates resultStatesNDA = new SetOfStates();
                for(int s : set) {
                    SetOfStates statesNDA = statesReachingLetter(s, l);
                    for(int st : statesNDA)
                        if(!resultStatesNDA.contains(st))
                            resultStatesNDA.add(st);
                }
                if (!resultStatesNDA.isEmpty()) {
                    if (!statesNDAToDA.contains(resultStatesNDA)) {
                        statesNDAToDA.add(resultStatesNDA); // on associe un nouvel état
                        stack.add(resultStatesNDA);
                    }
                    result.states[statesNDAToDA.indexOf(set)][l] = statesNDAToDA.indexOf(resultStatesNDA);
                }
            }

            // On vérifie si c'est un état initial ou final
            for (Integer st : set) {
                if (debut[st])
                    result.debut[statesNDAToDA.indexOf(set)] = true;
                if (fin[st])
                    result.fin[statesNDAToDA.indexOf(set)] = true;
            }
        }

        Automate resultRightNumberOfStates = new Automate(result, statesNDAToDA.size());
        return resultRightNumberOfStates;
    }

    private class Couple {
        SetOfStates set;
        int letter;

        public Couple(SetOfStates set, int letter) {
            this.set = set;
            this.letter = letter;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Couple))
                return false;
            return ((Couple) obj).set.equals(set) && ((Couple) obj).letter == letter;
        }

        @Override
        public int hashCode(){
            return set.hashCode()+letter;
        }

        @Override
        public String toString(){
            return "("+set+", "+(char)letter+"="+letter+")";
        }
    }

    /* Algorithme de Hopcroft. */
    public Automate minimize(String regEx) {
        // lettres présentes dans le mot.
        HashSet<Integer> lettres = new HashSet<>();
        for(char c : regEx.toCharArray()) lettres.add((int)c);

        ArrayList<SetOfStates> partition = new ArrayList<>();

        // on crée et remplit 2 partitions
        partition.add(new SetOfStates());
        partition.add(new SetOfStates());
        for (int i = 0; i < nbStates(); i++)
            if (fin[i])
                partition.get(0).add(i);
            else
                partition.get(1).add(i);

        ArrayList<Couple> W = new ArrayList<>();

        // On veut l'ensemble le plus petit
        SetOfStates smallerSet;
        if (partition.get(0).size() < partition.get(1).size())
            smallerSet = partition.get(0);
        else
            smallerSet = partition.get(1);

        // On remplit W
        for (int l : lettres)
            if(l>=0 && l<256)
                W.add(new Couple(smallerSet, l));

        SetOfStates x1, x2;
        while (!W.isEmpty()) {
            Couple Za = W.remove(W.size() - 1);;

            for (int p = 0; p<partition.size(); p++) {
                SetOfStates x = partition.get(p);

                x1 = new SetOfStates();
                x2 = new SetOfStates();
                for (int s : x) {
                    if (Za.set.contains(states[s][Za.letter]))
                        x1.add(s);
                    else
                        x2.add(s);
                }

                // si X est bien coupé par Za :
                // On met à jour la partition
                if (!x1.isEmpty() && !x2.isEmpty()) {

                    partition.remove(x);
                    partition.add(x1);
                    partition.add(x2);

                    // On met à jour W :
                    if (x1.size() < x2.size())
                        smallerSet = x1;
                    else
                        smallerSet = x2;

                    for(int m : lettres){
                        if(m>=0 && m<256){
                            Couple c = new Couple(x, m);

                            if (W.contains(c)) {
                                W.remove(c);
                                W.add(new Couple(x1, m));
                                W.add(new Couple(x2, m));
                            } else {
                                W.add(new Couple(smallerSet, m));
                            }
                        }
                    }
                }
            }
        }

        Automate result = new Automate(partition.size());

        int resultState=-1;
        for (SetOfStates set : partition){
            resultState = partition.indexOf(set);
            for(int state : set){
                if(debut[state])
                    result.debut[resultState]=true;

                for(int l=0; l<256; l++){
                    int stateOut = states[state][l];
                    if (stateOut != -1) {
                        for(SetOfStates s : partition)
                            if(s.contains(stateOut)) {
                                result.states[resultState][l] = partition.indexOf(s);
                                break;
                            }
                    }
                }

                if(fin[state])
                    result.fin[resultState]=true;
            }
        }

        return result;
    }

    public int nbStates() {
        return epsilon.length;
    }

    public String toString() {
        String s = "";

        for (int i = 0; i < this.nbStates(); i++) {
            s += i + " | ";
            for (int j = 0; j < 256; j++) {
                if (this.states[i][j] != -1) {
                    s += " [ " + (char) j + " : " + this.states[i][j] + " ] ";
                }
            }
            s += "| epsilons :" + epsilon[i].toString()+" ";
            if (this.debut[i] == true) {
                s += "| deb";
            }

            if (this.fin[i] == true) {
                s += "| fin";
            }

            s += "\n";
        }

        return s;
    }

    // Avec un automate déterministe (un seul état initial).
    public static boolean isWord(Automate a, String substring){
        int next;

        // on cherche l'état initial de l'automate.
        for(int s=0 ; s<a.nbStates() ; s++)
            if(a.debut[s]) {
                next=s;

                // pour chaque lettre du mot
                for(int i=0 ; i < substring.length() ; i++) {
                    if(a.fin[next])
                        return true;

                    int l = substring.charAt(i);
                    if(l<0 || l>=256)
                        return false;

                    // si la lettre est présente sur une transition, on continue.
                    if (a.states[next][l] != -1) {
                        next = a.states[next][l];
                    }
                    // sinon ce n'est pas le début du mot cherché.
                    else {
                        return false;
                    }
                }
                // si on finit le mot et qu'on est dans un état final, on a trouvé le mot cherché.
                if(a.fin[next])
                    return true;
            }

        return false;
    }

    public static ArrayList<Integer> getOccurencesOnLine(Automate a, String line){
        ArrayList<Integer> result = new ArrayList<>();;

        for(int i=0; i<line.length(); i++) {
            if(Automate.isWord(a, line.substring(i))) {
                result.add(i);
            }
        }

        return result;
    }

    public static ArrayList<TextPosition> getOccurencesOnText(ArrayList<String> text, String regEx){
        try{
            Automate a = Automate.fromTree(new RegEx(regEx).parse()).determinize().minimize(regEx);

            List<ArrayList<TextPosition>> tmp = text.parallelStream().map(e -> {
                ArrayList<TextPosition> result = new ArrayList<>();
                for(Integer colonne : getOccurencesOnLine(a, e)) {
                    result.add(new TextPosition(text.indexOf(e), colonne));
                }
                return result;
            }).collect(Collectors.toList());

            List<TextPosition> result;

            result = tmp.parallelStream().flatMap(Collection::parallelStream).collect(Collectors.toList());

            return (ArrayList<TextPosition>)result;

        }catch(Exception e){}
        return null;
    }
}