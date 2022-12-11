package structures;

/**
 * This is a class represents a pair of objects.
 *
 * @author Arthur Cremelie
 */

public class Pair<A,B> {
    /**
     * Variable keeping the first element of the pair.
     */
    private final A a;
    /**
     * Variable keeping the second element of the pair.
     */
    private final B b;

    /**
     * Constructor that creates a new pair given two object a and b.
     * @param a indicates the fist object.
     * @param b indicates the second object.
     */
    public Pair(A a, B b) {
        assert a != null;
        assert b != null;

        this.a = a;
        this.b = b;
    }

    /**
     * Get the first object of the pair.
     * @return The first object of the pair.
     */
    public A getA() { return a; }

    /**
     * Get the second object of the pair.
     * @return The second object of the pair.
     */
    public B getB() { return b; }

    @Override
    public int hashCode() { return a.hashCode() ^ b.hashCode(); }

    /**
     * Check if a given pair is equal to this pair.
     * @param o Pair The pair the check with.
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) return false;
        Pair pairo = (Pair) o;
        return this.a.equals(pairo.getA()) &&
                this.b.equals(pairo.getB());
    }

    /**
     * Convert a pair to a string by returning the first and second element between brackets.
     */
    public String toString() {
        return "(" + a + ", " + b + ")";
    }
}
