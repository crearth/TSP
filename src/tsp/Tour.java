package tsp;

import tsp.ts.DoublyLinkedList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Tour implements TourInterface {

    private List<Integer> tourList;
    private DoublyLinkedList doubleList;
    private final Graph graph;

    public Tour(Graph graph) {
        this.graph = graph;
    }

    public Tour(Tour another) {
        this.graph = another.getGraph();
        // not good practice
        if (another.getDoubleList() != null) {
            this.doubleList = new DoublyLinkedList(another.getTour());
        } else {
            this.tourList = new ArrayList<>(another.getTour());
        }
    }

    @Override
    public List<Integer> getTour() {
        // not good practice ??
        if (doubleList != null) {
            return doubleList.toList();
        }
        return tourList;
    }

    private Graph getGraph() {
        return graph;
    }

    public void setDoubleList(DoublyLinkedList list) {
        doubleList = list;
    }
    public DoublyLinkedList getDoubleList() {
        return doubleList;
    }

    @Override
    public int getTourLength() {
        int length = 0;
        List<Integer> tour = getTour();
        for (int i = 0; i < graph.getNumberOfVertices() - 1; i++) {
            length += graph.getDistance(tour.get(i), tour.get(i+1));
        }
        length += graph.getDistance(getTour().get(0), getTour().get(getTour().size()-1));
        return length;
    }

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
