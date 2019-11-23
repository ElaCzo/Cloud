package Online;

public class Edge<T> {
    protected T a;
    protected T b;

    public Edge(T a, T b){
        this.a=a;
        this.b=b;
    }

    public T getA(){
        return a;
    }

    public T getB(){
        return b;
    }

    @Override
    public boolean equals(Object o){
        return (o instanceof Edge
        && (((Edge)o).a==a && ((Edge)o).b==b
        || ((Edge)o).a==b && ((Edge)o).b==a)
        );
    }
}
