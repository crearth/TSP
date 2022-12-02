package tsp.ts;

import tsp.Graph;
import tsp.Pair;
import tsp.Tour;
import tsp.ts.DoublyLinkedList.Node;

import java.util.ArrayList;
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

    private int[][] tabuHelp;

    /**
     * Variable keeping the graph.
     */
    private final Graph graph;

    public TabuSearch(int maxIterations, Graph graph) {
        this.maxIterations = maxIterations;
        this.graph = graph;

        tabuList = new ArrayBlockingQueue<Pair<Integer, Integer>>(graph.getNumberOfVertices()/4);
        tabuHelp = new int[graph.getNumberOfVertices()][graph.getNumberOfVertices()];
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

        int costReduction = Integer.MIN_VALUE;
        Tour R = null;

        Pair<Integer, Integer> tabu = null;

        Node iPrev = tourList.getTail();
        Node iCurrent = tourList.getHead();
        Node temp = null;

        Node bestIPrev = null, bestICurrent = null, bestJCurrent = null, bestJNext = null;

        for (int i = 0; i < neighborhoodSize; i++) {
            DoublyLinkedList.Node jCurrent = iCurrent.getNext(iPrev);
            DoublyLinkedList.Node jNext = jCurrent.getNext(iCurrent);
            for (int j = i+1; j < neighborhoodSize; j++) {
                if (!isTabu(iCurrent.item,jCurrent.item)) {
                    int newCostReduction = calculateCostReduction(iPrev, iCurrent, jCurrent, jNext);
                    if (newCostReduction > costReduction) {
                        bestIPrev = iPrev;
                        bestICurrent = iCurrent;
                        bestJCurrent = jCurrent;
                        bestJNext = jNext;
                        costReduction = newCostReduction;
                        tabu = new Pair<Integer, Integer>(iCurrent.item, jCurrent.item);
                    }
                }
                temp = jCurrent;
                jCurrent = jNext;
                jNext = jNext.getNext(temp);
            }
            temp = iCurrent;
            iCurrent = iCurrent.getNext(iPrev);
            iPrev = temp;
        }
        tourList.swap(bestIPrev, bestICurrent, bestJCurrent, bestJNext);
        R = new Tour(graph);
        R.setDoubleList(tourList);
        addTabu(tabu);
        return R;
    }

    private int calculateCostReduction(Node iPrev, Node iCurrent, Node jCurrent, Node jNext) {
        return graph.getDistance(iPrev.item,jCurrent.item) + graph.getDistance(iCurrent.item, jNext.item)
                - graph.getDistance(iPrev.item, iCurrent.item) - graph.getDistance(jCurrent.item, jNext.item);
    }

    public boolean isTabu(int i,int j) {
        return tabuHelp[i-1][j-1] == 1 || tabuHelp[j-1][i-1] == 1;
    }

    private void addTabu(Pair<Integer, Integer> tabu) {
        try {
            tabuList.add(tabu);
        } catch (IllegalStateException e) {
            Pair<Integer, Integer> removedPair = tabuList.poll();
            tabuHelp[removedPair.getA()-1][removedPair.getB()-1] = 0;
            tabuHelp[removedPair.getB()-1][removedPair.getA()-1] = 0;
            tabuList.add(tabu);
        }
        tabuHelp[tabu.getA()-1][tabu.getB()-1] = 1;
        tabuHelp[tabu.getB()-1][tabu.getA()-1] = 1;
    }
}
