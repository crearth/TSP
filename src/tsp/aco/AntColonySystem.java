package tsp.aco;

import tsp.Graph;
import tsp.Tour;
import tsp.ts.DoublyLinkedList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AntColonySystem {
    private Graph graph;
    private double[][] pheromoneTrail;

    private int numberOfAnts;

    private double evaporationRateB; //2 - 5
    private double alpha = 1;



    public AntColonySystem(Graph graph, int numberOfAnts) {
        this.graph = graph;
        this.numberOfAnts = numberOfAnts;
        evaporationRateB = 2;

        initializePheromoneTrailACS();
    }

    public Graph getGraph() {
        return graph;
    }

    public double getPheromoneValue(int i, int j) {
        return pheromoneTrail[i-1][j-1];
    }

    public double getEvaporationRateB() {
        return evaporationRateB;
    }

    public Ant getBestAnt() {
        Ant bestAnt = null;
        Tour bestTour = null;
        int bestTourLength = Integer.MAX_VALUE;
        for (int i = 1; i <= numberOfAnts; i++) {
            Ant ant = new Ant(graph, this);
            Tour antTour = ant.createTour();
            int antTourLength = antTour.getTourLength();
            if (antTourLength < bestTourLength) {
                bestAnt = ant;
                bestTour = antTour;
                bestTourLength = antTourLength;
            }
        }
        return bestAnt;
    }

    //TODO iterate getBestAnt with pheromone trail update

    /**
     * This is used with Ant System
     * Initialize the pheromones to the number of ants divided by the cost of the nearest neighbor solution:  m/C^(nn).
     * This is done so that the pheromone values aren't too low and thus the ants don't create biased tours.
     */
    private void initializePheromoneTrailAS() {
        int graphDimension = graph.getNumberOfVertices();
        int nearestNeighborCost = nearestNeighbor().getTourLength();
        for (int i = 0; i < graphDimension; i++) {
            for (int j = 0; j < graphDimension; j++) {
                pheromoneTrail[i][j] = (double) numberOfAnts / nearestNeighborCost;
            }
        }
    }

    /**
     * This is used with Ant Colony System
     * Initialize the pheromones to: 1 / (n * C^(nn)), C^(nn) the cost of a nearest neighbor solution.
     * This is done so that the pheromone values aren't too low and thus the ants don't create biased tours.
     */
    private void initializePheromoneTrailACS() {
        int graphDimension = graph.getNumberOfVertices();
        int nearestNeighborCost = nearestNeighbor().getTourLength();
        for (int i = 0; i < graphDimension; i++) {
            for (int j = 0; j < graphDimension; j++) {
                pheromoneTrail[i][j] = (double) 1 / (graphDimension * nearestNeighborCost);
            }
        }
    }

    private Tour nearestNeighbor() {
        Tour tour = new Tour(graph);
        List<Integer> tourList = tour.getTour();
        ArrayList<Integer> verticesNotTaken = new ArrayList<Integer>(graph.getVertices());
        int current = ThreadLocalRandom.current().nextInt(1, graph.getNumberOfVertices() + 1);
        verticesNotTaken.remove((Integer) current);
        tourList.add(current);
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
            verticesNotTaken.remove((Integer) current);
            tourList.add(current);
        }
        return tour;
    }
}
