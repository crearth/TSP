package tsp.ts;

import tsp.Tour;

public interface TabuSearchInterface {
    Tour initialSolution();
    Tour tabuSearch(Tour initialSolution);
    void twoOpt(DoublyLinkedList tour, int i, int j);
}
