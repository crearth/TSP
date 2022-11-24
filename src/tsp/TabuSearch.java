package tsp;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class TabuSearch implements TabuSearchInterface{
    /**
     * Variable keeping the maximum amount of iterations.
     */
    private int maxIterations;
    /**
     * Variable keeping the graph.
     */
    private Graph graph;
    /**
     * Variable keeping the tabu list, the moves that are taboo.
     */
    private ArrayBlockingQueue<Pair<Integer, Integer>> tabuList;

    public TabuSearch(int maxIterations, Graph graph) {
        this.maxIterations = maxIterations;
        this.graph = graph;
        tabuList = new ArrayBlockingQueue<>(graph.getNumberOfVertices());

        //TODO dit is om te testen
        initialSolution();
    }

    /**
     * The initial solution, implemented with Nearest Neighbor
     * @return
     */
    @Override
    public DoublyLinkedList initialSolution() {
        DoublyLinkedList tour = new DoublyLinkedList();
        Collection<Integer> verticesNotTaken = graph.getVertices();
        int current = ThreadLocalRandom.current().nextInt(1, graph.getNumberOfVertices() + 1);
        verticesNotTaken.remove(current);
        tour.insertItemEnd(current);
        int bestVertex = 0;
        while (verticesNotTaken.size() > 0) {
            int bestDistance = Integer.MAX_VALUE;
            for (int i : verticesNotTaken) {
                int distance = graph.getDistance(current, i);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestVertex = i;
                }
            }
            current = bestVertex;
            verticesNotTaken.remove(current);
            tour.insertItemEnd(current);
        }
        tour.printItems();
        return tour;
    }

    @Override
    public void tabuSearch() {

    }

    @Override
    public void twoOpt(int i, int j) {

    }
}
