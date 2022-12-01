package tsp.ts;

import tsp.Graph;
import tsp.Pair;
import tsp.Tour;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    public Tour getBestCandidate(Tour tour) {
        int neighborhoodSize = graph.getNumberOfVertices();
        DoublyLinkedList tourList = tour.getDoubleList();

        int RLength = Integer.MAX_VALUE;
        Tour R = null;

        Pair<Integer, Integer> tabu = null;

        tourList.print();
        DoublyLinkedList.Node iPrev = tourList.getTail();
        DoublyLinkedList.Node iCurrent = tourList.getHead();
        DoublyLinkedList.Node temp = null;

        for (int i = 0; i < neighborhoodSize; i++) {
            DoublyLinkedList.Node jCurrent = iCurrent.getNext(iPrev);
            DoublyLinkedList.Node jNext = jCurrent.getNext(iCurrent);
            for (int j = i+1; j < neighborhoodSize; j++) {
                temp = jCurrent;
                System.out.println(iPrev + ", " + iCurrent + ", " + jCurrent + ", " +  jNext);
                if (isTabu(iCurrent.item,jCurrent.item)) { continue; }
                Tour W = new Tour(tour);
                W.getDoubleList().swap(iPrev, iCurrent, jCurrent, jNext);
                System.out.print("Doubly Linked List na swap: ");
                W.getDoubleList().print();
                int WLength = W.getTourLength();
                //int costReduction = calculateCostReduction(R, i,j);
                //int newCost = RLength - costReduction;
                if (WLength < RLength) {
                    RLength = WLength;
                    R = W;
                    tabu = new Pair<Integer, Integer>(iCurrent.item, jCurrent.item);
                }
                jCurrent = jNext;
                jNext = jNext.getNext(temp);
            }
            temp = iCurrent;
            iCurrent = iCurrent.getNext(iPrev);
            iPrev = temp;
        }
        addTabu(tabu);
        return R;
    }

    private int calculateCostReduction(Tour R, int i, int j) {
        if (R == null) {
            return 0;
        }
        List tour = R.getTour();
        int iPrev = (int) tour.get(tour.indexOf((Integer) i)-1);
        int jNext = (int) tour.get(tour.indexOf((Integer) j)+1);
        return graph.getDistance(iPrev, j) + graph.getDistance(i, jNext) - graph.getDistance(iPrev, i) - graph.getDistance(j, jNext);
    }

    public boolean isTabu(int i,int j) {
        Pair<Integer, Integer> pair = new Pair(i, j);
        Pair<Integer, Integer> pairInverted = new Pair(j, i);
        return tabuList.contains(pair) || tabuList.contains(pairInverted);
    }

    private void addTabu(Pair<Integer, Integer> tabu) {
        try {
            tabuList.add(tabu);
        } catch (IllegalStateException e) {
            tabuList.poll();
            tabuList.add(tabu);
        }
    }

    @Override
    public void twoOpt(DoublyLinkedList tour, int i, int j) {
        tour.twoOpt(tour.searchItem(i),tour.searchItem(j));
    }
}
