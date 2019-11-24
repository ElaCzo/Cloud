package Online;

public class EdgeJaccard<T> extends Edge {
    private double jacquard;

    public EdgeJaccard(T a, T b, double jacquard){
        super(a, b);
        this.jacquard=jacquard;
    }

    public double getJacquard() {
        return jacquard;
    }
}
