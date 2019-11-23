package Online;

public class DistanceJaccard<T> extends Edge {
    private double jacquard;

    public DistanceJaccard(T a, T b, double jacquard){
        super(a, b);
        this.jacquard=jacquard;
    }

    public double getJacquard() {
        return jacquard;
    }
}
