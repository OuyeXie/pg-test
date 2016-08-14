package com.ouyexie.pg.data;


import java.util.Objects;

/**
 * @author Ouye Xie
 */
public class LoosePair<F, S> {
    public F first;
    public S second;

    /**
     * Constructor for a Pair.
     *
     * @param first  the first object in the Pair
     * @param second the second object in the pair
     */
    public LoosePair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Convenience method for creating an appropriately typed pair.
     *
     * @param a the first object in the Pair
     * @param b the second object in the pair
     * @return a Pair that is templatized with the types of a and b
     */
    public static <A, B> LoosePair<A, B> create(A a, B b) {
        return new LoosePair<A, B>(a, b);
    }

    /**
     * Checks the two objects for equality by delegating to their respective
     * {@link Object#equals(Object)} methods.
     *
     * @param o the {@link LoosePair} to which this one is to be checked for equality
     * @return true if the underlying objects of the Pair are both considered
     * equal
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LoosePair)) {
            return false;
        }
        LoosePair<?, ?> p = (LoosePair<?, ?>) o;
        return (Objects.equals(p.first, first) && Objects.equals(p.second, second)) || (Objects.equals(p.first, second) && Objects.equals(p.second, first));
    }

    /**
     * Compute a hash code using the hash codes of the underlying objects
     *
     * @return a hashcode of the Pair
     */
    @Override
    public int hashCode() {
        return (first == null ? 0 : first.hashCode()) + (second == null ? 0 : second.hashCode());
    }
}
