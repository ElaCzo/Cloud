package Online;

public class HeapSort {
    private static void swap(double[] a, int[] res, int k, int m)
    {
        double swap = a[k];
        a[k]=a[m];
        a[m]=swap;

        int swap2 = res[k];
        res[k]=res[m];
        res[m]=swap2;
    }

    private static void heapify(double[] tree, int[] res, int node, int n)
    {
        int k = node;
        int j = 2 * k;

        while (j <= n)
        {
            if ((j < n) && (tree[j] < tree[j + 1]))
                j++;

            if (tree[k] < tree[j])
            {
                swap(tree, res, k, j);
                k = j;
                j = 2 * k;
            }
            else
                break;
        }
    }

    public static void heapsort(double[] tree, int[] res)
    {
        for (int i = tree.length >> 1; i >= 0; i--)
            heapify(tree, res, i, tree.length - 1);

        for (int i = tree.length - 1; i >= 1; i--)
        {
            swap(tree, res, i, 0);
            heapify(tree, res, 0, i - 1);
        }
    }
}
