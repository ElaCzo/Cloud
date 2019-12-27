package Online;

public class HeapSort {

    /***** SWAPS *****/

    /* échange le contenu des indices k et m dans t. */
    private static void swap(double[] t, int k, int m)
    {
        double swap = t[k];
        t[k]=t[m];
        t[m]=swap;;
    }

    /* échange le contenu des indices k et m dans t. */
    private static void swap(int[] t, int k, int m)
    {
        int swap = t[k];
        t[k]=t[m];
        t[m]=swap;;
    }

    /***** HEAPSORT *****/

    private static void heapify(double[] tree, int[] res, int node, int n) {
        int k = node;
        int j = 2 * k;

        while (j <= n) {
            if ((j < n) && (tree[j] < tree[j + 1]))
                j++;

            if (tree[k] < tree[j]) {
                swap(tree, k, j);
                swap(res, k, j);
                k = j;
                j = 2 * k;
            }
            else
                break;
        }
    }

    /* Trie les indices de tree et son tableau d'indices par ordre croissant
    avec le tri par tas. */
    private static void heapsort(double[] tree, int[] indices) {
        for (int i = tree.length >> 1; i >= 0; i--)
            heapify(tree, indices, i, tree.length - 1);

        for (int i = tree.length - 1; i >= 1; i--) {
            swap(tree, i, 0);
            swap(indices, i, 0);
            heapify(tree, indices, 0, i - 1);
        }
    }

    /* Trie tree et renvoie les indices de tree par ordre croissant avec le
    tri par tas. */
    public static int[] heapsort (double[] tree) {
        int[] indices = new int[tree.length];
        double[] treetmp = new double[tree.length];

        for(int i=0; i<tree.length; i++) {
            indices[i] = i;
            treetmp[i]= tree[i];
        }

        heapsort(treetmp, indices);
        return indices;
    }
}
