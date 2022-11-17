package tsp;

import java.util.Collection;

public class Graph implements GraphInterface{
    @Override
    public Collection<Integer> getVertices() {
        return null;
    }

    @Override
    public int getNumberOfVertices() {
        return 0;
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
