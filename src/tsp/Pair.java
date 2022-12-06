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

    public A getA() { return a; }
    public B getB() { return b; }

    @Override
    public int hashCode() { return a.hashCode() ^ b.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) return false;
        Pair pairo = (Pair) o;
        return this.a.equals(pairo.getA()) &&
                this.b.equals(pairo.getB());
    }

    public String toString() {
        return "(" + a + ", " + b + ")";
    }
}
