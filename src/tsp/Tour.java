package tsp;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Tour implements TourInterface {

    private DoublyLinkedList doubleList;
    private final Graph graph;

    public Tour(Graph graph) {
        this.graph = graph;
    }

    public Tour(Tour another) {
        this.graph = another.getGraph();
        this.doubleList = new DoublyLinkedList(another.getTour());
    }

    @Override
    public List<Integer> getTour() {
        return doubleList.toList();
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
        for (int i = 0; i < graph.getNumberOfVertices() - 1; i++) {
            length += graph.getDistance(getTour().get(i), getTour().get(i+1));
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
