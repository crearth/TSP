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
    /**
     * Help variable keeping the tabu information of a vertex (i,j).
     * If (i,j) is tabu, tabuHelp[i][j] = 1.
     * If (i,j) is not tabu, tabuHelp[i][j] = 0.
     */
    private int[][] tabuHelp;
    /**
     * Variable keeping the graph.
     */
    private final Graph graph;

    /**
     * Constructor for the Tabu Search class. The constructor initiates the maximum number of iterations to the given
     * maximum number and sets the graph of the problem to the given graph.
     * This constructor also initializes the tabu list, tabu list length and help variable tabuHelp.
     * @param maxIterations The maximum number of iterations.
     * @param graph The graph of the problem.
     */
    public TabuSearch(int maxIterations, Graph graph) {
        this.maxIterations = maxIterations;
        this.graph = graph;

        int tabuListLength = graph.getNumberOfVertices() / 2;

        tabuList = new ArrayBlockingQueue<Pair<Integer, Integer>>(tabuListLength);
        tabuHelp = new int[graph.getNumberOfVertices()][graph.getNumberOfVertices()];
    }

    /**
     * The initial (greedy) solution, implemented with Nearest Neighbour. It creates a doubly linked list and creates
     * a new Tour object with the doubly linked list to the doubly linked list just created.
     * @return Tour The nearest neighbour solution of the problem.
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

    /**
     * Runs Tabu Search with a initialSolution and returns the best tour created.
     * @return Tour The best tour created.
     */
    public Tour getBestTour() {
        return tabuSearch(initialSolution());
    }

    /**
     * Initializes the initial solution to the best tour and then searches for a better tour with the tabu search
     * heuristic, with a maximum number of iterations.
     * @param initialSolution Tour The initial Tour (Nearest Neighbour).
     * @return Tour The best found tour.
     */
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

        int costReduction = Integer.MAX_VALUE;
        Tour bestCandidate = null;

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
                    if (newCostReduction < costReduction) {
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
        bestCandidate = new Tour(graph);
        bestCandidate.setDoubleList(tourList);
        addTabu(tabu);
        return bestCandidate;
    }

    /**
     * Calculate the cost reduction based on the pointers of 4 vertices. We simulate the cost reduction
     * of a 2-opt swap. I.e. remove the cost of edges (i-1, i) and (j, j+1) and add the cost of edges
     * (i-1, j) and (i,j+1). The cost of the vertices between these points do not change because of the nature
     * of a 2-opt swap.
     * @param iPrev Node The pointer of the previous node (i-1).
     * @param iCurrent Node The pointer of the current node (i).
     * @param jCurrent Node The pointer of the second current node (j).
     * @param jNext Node The pointer of the next node of the second current node (j+1).
     * @return int The calculated costreduction.
     */
    private int calculateCostReduction(Node iPrev, Node iCurrent, Node jCurrent, Node jNext) {
        return graph.getDistance(iPrev.item,jCurrent.item) + graph.getDistance(iCurrent.item, jNext.item)
                - graph.getDistance(iPrev.item, iCurrent.item) - graph.getDistance(jCurrent.item, jNext.item);
    }

    /**
     * Check if a move (i,j) is tabu or not by using the tabuHelp matrix. Position [i][j] in the matrix is
     * 1 if move (i,j) or (j,i) is tabu, else [i][j] is 0.
     * @param i int Vertex i.
     * @param j int Vertex j.
     * @return True if move (i,j) or (j, i) is tabu.
     */
    public boolean isTabu(int i,int j) {
        return tabuHelp[i-1][j-1] == 1 || tabuHelp[j-1][i-1] == 1;
    }

    /**
     * Update the tabu list by the given Pair of vertices. First the move is added to the tabuList queue, if the
     * queue is full, remove the first element and save it. Then add the Pair to the tabu list. The help matrix
     * is also updated at [i][j] and [j][i]. If the queue was full, the help matrix is also updated at the
     * indices of the removed Pair (that was saved).
     * @param tabu Pair A pair of vertices that has to be made tabu.
     */
    private void addTabu(Pair<Integer, Integer> tabu) {
        try {
            tabuList.add(tabu);
        } catch (IllegalStateException e) {
            Pair<Integer, Integer> removedPair = tabuList.poll();
            assert removedPair != null;
            tabuHelp[removedPair.getA()-1][removedPair.getB()-1] = 0;
            tabuHelp[removedPair.getB()-1][removedPair.getA()-1] = 0;
            tabuList.add(tabu);
        }
        tabuHelp[tabu.getA()-1][tabu.getB()-1] = 1;
        tabuHelp[tabu.getB()-1][tabu.getA()-1] = 1;
    }
}
