package tsp.ts;

import tsp.Graph;
import tsp.Pair;
import tsp.Tour;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class TabuSearch implements TabuSearchInterface {
    /**
     * Variable keeping the maximum amount of iterations.
     */
    private final int maxIterations;
    /**
     * Variable keeping the tabu list.
     */
    ArrayBlockingQueue<Pair<Integer, Integer>> tabuList;
    /**
     * Variable keeping the graph.
     */
    private final Graph graph;

    public TabuSearch(int maxIterations, Graph graph) {
        this.maxIterations = maxIterations;
        this.graph = graph;

        tabuList = new ArrayBlockingQueue<Pair<Integer, Integer>>(graph.getNumberOfVertices());
    }

    /**
     * The initial solution, implemented with Nearest Neighbor
     * @return
     */
    @Override
    public Tour initialSolution() {
        Tour tour = new Tour(graph);
        DoublyLinkedList tourLinked = new DoublyLinkedList();
        ArrayList<Integer> verticesNotTaken = new ArrayList<Integer>(graph.getVertices());
        int current = ThreadLocalRandom.current().nextInt(1, graph.getNumberOfVertices() + 1);
        verticesNotTaken.remove((Integer) current); // O(n)
        tourLinked.addEnd(current);
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
            verticesNotTaken.remove((Integer) current); // O(n)
            tourLinked.addEnd(current);
        }
        tour.setDoubleList(tourLinked);
        return tour;
    }

    public Tour getBestTour() {
        return tabuSearch(initialSolution());
    }

    @Override
    public Tour tabuSearch(Tour initialSolution) {
        Tour s = initialSolution;
        Tour bestTour = s;

        for (int maxI = 0; maxI < maxIterations; maxI++) {
            s = getBestCandidate(new Tour(s));
            if (s.getTourLength() < bestTour.getTourLength()) {
                bestTour = s;
            }
        }
        return bestTour;
    }

    public Tour getBestCandidate(Tour initialTour) {
        int neighborhoodSize = graph.getNumberOfVertices();

        Integer RLength = Integer.MAX_VALUE;
        Tour R = null;

        Pair tabu = null;

        for (int i = 0; i < neighborhoodSize; i++) {
            for (int j = 0; j < neighborhoodSize; j++) {
                if (isTabu(i,j)) {
                    continue;
                }
                Tour W = new Tour(initialTour);
                twoOpt(W.getDoubleList(), i, j);
                int WLength = W.getTourLength();
                if (WLength < RLength) {
                    RLength = WLength;
                    R = W;
                    tabu = new Pair(i,j);
                }
            }
        }
        try {
            tabuList.add(tabu);
        } catch (IllegalStateException e) {
            tabuList.poll();
            tabuList.add(tabu);
        }
        return R;
    }

    public boolean isTabu(int i, int j) {
        Pair pair = new Pair(i, j);
        Pair pairInverted = new Pair(j, i);
        return tabuList.contains(pair) || tabuList.contains(pairInverted);
    }

    @Override
    public void twoOpt(DoublyLinkedList tour, int i, int j) {
        tour.twoOpt(tour.searchItem(i),tour.searchItem(j));
    }
}
