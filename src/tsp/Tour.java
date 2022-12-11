package tsp;

import structures.DoublyLinkedList;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a class represents a tour. It implements the class TourInteface.
 *
 * @author Arthur Cremelie
 */

public class Tour implements TourInterface {
    /**
     * Variable keeping the tour as a list (used for Tabu Search and Ant Colony System).
     */
    private List<Integer> tourList = new ArrayList<Integer>();
    /**
     * Variable keeping the tour as a doubly linked list (used for Tabu Search).
     */
    private DoublyLinkedList doubleList = null;
    /**
     * Variable keeping the graph of the tour.
     */
    private final Graph graph;

    /**
     * Constructor creating a tour with the given graph.
     * @param graph Graph The graph that belongs to the tour.
     */
    public Tour(Graph graph) {
        this.graph = graph;
    }

    /**
     * Constructor creating a tour with the given graph and list.
     * @param graph Graph The graph that belongs to the tour.
     * @param list List The list of the tour.
     */
    public Tour(Graph graph, List<Integer> list) {
        this(graph);
        tourList = new ArrayList<Integer>(list);
    }

    /**
     * Constructor creating a copy of the given tour.
     * @param another Tour The tour to copy.
     */
    public Tour(Tour another) {
        this.graph = another.getGraph();
        if (another.getDoubleList() != null) {
            this.doubleList = new DoublyLinkedList(another.getTour());
        } else {
            this.tourList = new ArrayList<>(another.getTour());
        }
    }

    /**
     * Get the tour as a list.
     * @return List The list of the tour.
     */
    @Override
    public List<Integer> getTour() {
        // not good practice ??
        if (doubleList != null) {
            return doubleList.toList();
        }
        return tourList;
    }

    /**
     * Get the graph of the tour.
     * @return Graph The graph of the tour.
     */
    private Graph getGraph() {
        return graph;
    }

    /**
     * Set the doubly linked list of the tour to the given doubly linked list.
     * @param list DoublyLinkedList
     */
    public void setDoubleList(DoublyLinkedList list) {
        doubleList = list;
    }

    /**
     * Get the doubly linked list of the tour (used in Tabu Search).
     * @return DoublyLinkedList
     */
    public DoublyLinkedList getDoubleList() {
        return doubleList;
    }

    /**
     * Get the total length of the tour by iterating the nodes in the list and checking the distance between
     * each node i and i + 1.
     * @return int The total length of the tour.
     */
    @Override
    public int getTourLength() {
        int length = 0;
        List<Integer> tour = getTour();
        for (int i = 0; i < graph.getNumberOfVertices() - 1; i++) {
            length += graph.getDistance(tour.get(i), tour.get(i+1));
        }
        length += graph.getDistance(tour.get(0), tour.get(tour.size()-1));
        return length;
    }

    /**
     * Get the total length of the canonical tour by iterating the nodes in order 1, 2, 3, ... n and checking
     * the distance between each node i and i + 1. This is used to test the correctness of the distancefunction.
     * @return int The total length of the canonical tour.
     */
    public int getCanonicalTourLength() {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= graph.getNumberOfVertices(); i++) {
            list.add(i);
        }
        int length = 0;
        for (int i = 0; i < graph.getNumberOfVertices() - 1; i++) {
            length += graph.getDistance(list.get(i), list.get(i+1));
            }
        length += graph.getDistance(list.get(0), list.get(list.size()-1));
        return length;
    }

}
