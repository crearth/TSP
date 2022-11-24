package tsp;

public interface TabuSearchInterface {
    DoublyLinkedList initialSolution();
    void tabuSearch();
    void twoOpt(DoublyLinkedList tour, int i, int j);
}
