package tsp;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

public class Graph implements GraphInterface{

    private final Data data;

    public Graph(String tspProblem) throws FileNotFoundException {
        data = new Data(tspProblem);
    }
    @Override
    public Collection<Integer> getVertices() {
        Collection<Integer> vertices = new ArrayList<>();
        for(int i = 1; i <= data.getDimension(); i++){
            vertices.add(i);
        }
        return vertices;
    }

    @Override
    public int getNumberOfVertices() {
        return data.getDimension();
    }

    @Override
    public int getDistance(int i, int j) {
        return data.getDistanceMatrix()[i-1][j-1];
    }

    @Override
    public Tour getTabuSearchBestTour(int maxNumberOfIterations) {
        TabuSearch tabuSearch = new TabuSearch(maxNumberOfIterations, this);
        return tabuSearch.getBestTour();
    }

    @Override
    public Tour getOtherHeuristicBestTour(int maxNumberOfIterations) {
        return null;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Graph burma = new Graph("burma14");
        Tour burmaTour = burma.getTabuSearchBestTour(1000);
        System.out.println(burmaTour.getTourLength());
    }
}
