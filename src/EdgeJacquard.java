package Online;

public class EdgeJacquard<T> extends Edge {
    private double jacquard;

    public EdgeJacquard(T a, T b, double jacquard){
        super(a, b);
        this.jacquard=jacquard;
    }

    public double getJacquard() {
        return jacquard;
    }
}
