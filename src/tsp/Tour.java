package tsp;

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
        for (int i = 0; i <= getTour().size(); i++) {
            length += graph.getDistance(getTour().get(i), getTour().get(i+1));
        }
        return length;
    }
}
