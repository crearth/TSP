package tsp;

public interface TabuSearchInterface {
    Tour initialSolution();
    void tabuSearch(Tour initialSolution);
    void twoOpt(DoublyLinkedList tour, int i, int j);
}
