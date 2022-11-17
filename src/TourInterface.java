package be.KULeuven;

import java.util.List;

public interface TourInterface {

    /**
     * Returns an ordered list representing a tour of the graph. It should always contain all elements of the graph
     * exactly once in a specific order.
     */
    List<Integer> getTour();

    /**
     * Returns the length of the tour. This is the sum of all distances between nodes i and j if j follows i in the list
     * given by getTour() or if i and j are the first and last element.
     */
    int getTourLength();
}
