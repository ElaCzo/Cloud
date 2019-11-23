package Online;

public class HeapSort {
    private static void Echange(int[] a, int k, int m)
    {
        int swap = a[k];
        a[k]=a[m];
        a[m]=swap;
    }

    private static void Tamiser(int[] arbre, int noeud, int n)
    {
        int k = noeud;
        int j = 2 * k;

        while (j <= n)
        {
            if ((j < n) && (arbre[j] < arbre[j + 1]))
                j++;

            if (arbre[k] < arbre[j])
            {
                Echange(arbre, k, j);
                k = j;
                j = 2 * k;
            }
            else
                break;
        }
    }

    public static void TriParTas(int[] arbre)
    {
        for (int i = arbre.length >> 1; i >= 0; i--)
            Tamiser(arbre, i, arbre.length - 1);

        for (int i = arbre.length - 1; i >= 1; i--)
        {
            Echange(arbre, i, 0);
            Tamiser(arbre, 0, i - 1);
        }
    }
}
