package tsp;

import java.io.FileNotFoundException;
import java.util.Collection;

public class Graph implements GraphInterface{
    private Data data;

    private int numberOfVertices;

    public Graph(String tspProblem) throws FileNotFoundException {
        data = new Data(tspProblem);

        numberOfVertices = data.getDimension();
    }
    @Override
    public Collection<Integer> getVertices() {
        return null;
    }

    @Override
    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    @Override
    public int getDistance(int i, int j) {
        return 0;
    }

    @Override
    public Tour getTabuSearchBestTour(int maxNumberOfIterations) {
        return null;
    }

    @Override
    public Tour getOtherHeuristicBestTour(int maxNumberOfIterations) {
        return null;
    }
}
