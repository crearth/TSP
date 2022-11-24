package tsp;

public interface TabuSearchInterface {
    DoublyLinkedList initialSolution();
    void tabuSearch();
    void twoOpt(int i, int j);
}
