package tsp;

public class Pair<A,B> {
    private final A a;
    private final B b;

    public Pair(A a, B b) {
        assert a != null;
        assert b != null;

        this.a = a;
        this.b = b;
    }

    public A getLeft() { return a; }
    public B getRight() { return b; }

    @Override
    public int hashCode() { return a.hashCode() ^ b.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) return false;
        Pair pairo = (Pair) o;
        return this.a.equals(pairo.getLeft()) &&
                this.b.equals(pairo.getRight());
    }
}
