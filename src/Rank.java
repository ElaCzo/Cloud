package Online;


public class Rank {
    public Rank() {
    }

    public static void main(String[] args) {
        double tab[][] = new double[6][6];
        tab[0] = new double[] { 0, 0, 0, 0, 0, 0.1 };
        tab[1] = new double[] { 1 / (double) 3, 0, 3 / (double) 23, 0, 4 / (double) 17, 0.1 };
        tab[2] = new double[] { 0, 5 / (double) 18, 0, 0, 0, 0.1 };
        tab[3] = new double[] { 1 / (double) 9, 0, 0, 0, 3 / (double) 17, 0.1 };
        tab[4] = new double[] { 0, 10 / (double) 18, 0, 0, 0, 0.1 };
        tab[5] = new double[] { 5 / (double) 9, 3 / (double) 18, 20 / (double) 23, 1, 10 / (double) 17, .5 };
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++)
                System.out.print(tab[i][j] + " ");
            System.out.println();
        }
        System.out.println();
        System.out.println();

        double v[] = new double[] { 1 / (double) 6, 1 / (double) 6, 1 / (double) 6, 1 / (double) 6, 1 / (double) 6,
                1 / (double) 6 };
        double vNext[] = new double[] { 1 / (double) 6, 1 / (double) 6, 1 / (double) 6, 1 / (double) 6, 1 / (double) 6,
                1 / (double) 6 };

        for (int r = 0; r < 300; r++) {
            for (int i = 0; i < 6; i++)
                System.out.print(v[i] + " ");
            System.out.println();
            for (int i = 0; i < 6; i++) {
                vNext[i] = 0;
                for (int j = 0; j < 6; j++) {
                    vNext[i] += v[j] * tab[i][j];
                }
            }
            for (int i = 0; i < 6; i++) {
                v[i] = vNext[i];
            }
        }

        for (int i = 0; i < 6; i++)
            System.out.print(v[i] + " ");
        System.out.println();
    }
}