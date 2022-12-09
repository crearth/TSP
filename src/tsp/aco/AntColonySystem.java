package tsp.aco;

import tsp.Graph;
import tsp.Tour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This is a class represents the algorithm of Ant Colony System.
 *
 * @author Arthur Cremelie
 */

public class AntColonySystem {
    private Graph graph;
    private double[][] pheromoneTrail;

    private int numberOfAnts;

    private double alpha = 1;
    private double beta; //2 - 5
    private double evaporationRate;
    private double localEvaporationRate;
    private double initialPheromoneValueACS;
    /**
     * Variable keeping the maximum amount of iterations.
     */
    private final int maxIterations;



    public AntColonySystem(int maxIterations, Graph graph) {
        this.maxIterations = maxIterations;
        this.graph = graph;
        pheromoneTrail = new double[graph.getNumberOfVertices()][graph.getNumberOfVertices()];
        numberOfAnts = 10; // 10 is optimal (book)
        beta = 2;
        evaporationRate = 0.1;
        localEvaporationRate = 0.1;

        setInitialPheromoneValueACS();
    }

    public Graph getGraph() {
        return graph;
    }

    public double getPheromoneValue(int i, int j) {
        return pheromoneTrail[i-1][j-1];
    }

    public double getBeta() {
        return beta;
    }

    public double getLocalEvaporationRate() {
        return localEvaporationRate;
    }

    public double getInitialPheromoneValueACS() {
        return initialPheromoneValueACS;
    }

    public Tour getBestTour() {
        return antColonySystem();
    }

    public Tour antColonySystem() {
        initializePheromoneTrailACS();
        Tour bestTour = null;
        Ant sAnt = null;
        Tour s = null;
        for (int maxI = 0; maxI < maxIterations; maxI++) {
            sAnt = getBestAnt();
            s = sAnt.getTour();
            updatePheromoneTrail(sAnt);
            if (bestTour == null || s.getTourLength() < bestTour.getTourLength()) {
                bestTour = s;
            }
        }
        return bestTour;
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

    /**
     * Update the pheromone trail with data from the best ant.
     * @param bestAnt The ant that produced the best tour.
     */
    private void updatePheromoneTrail(Ant bestAnt) {
        Tour tourAnt = bestAnt.getTour();
        double tourAntCost = tourAnt.getTourLength();
        ArrayList<Integer> tourAntList = (ArrayList<Integer>) tourAnt.getTour();
        for (int v = 0; v < tourAntList.size() - 1; v++){
            int i = tourAntList.get(v);
            int j = tourAntList.get(v+1);
            double value = (1 - evaporationRate) * getPheromoneValue(i, j) + evaporationRate * (1/tourAntCost);
            updatePheromoneTrail(i, j, value);
        }
    }

    /**
     * Update the pheromone trail between vertex i and j with the given value.
     * @param i The fist vertex.
     * @param j The second vertex.
     * @param value The value to change the pheromone trail with.
     */
    public void updatePheromoneTrail(int i, int j, double value) {
        pheromoneTrail[i-1][j-1] = value;
    }

    /**
     * This is used with Ant System
     * Initialize the pheromones to the number of ants divided by the cost of the nearest neighbor solution:  m/C^(nn).
     * This is done so that the pheromone values aren't too low and thus the ants don't create biased tours.
     */
    private void initializePheromoneTrailAS() {
        int graphDimension = graph.getNumberOfVertices();
        int nearestNeighborCost = nearestNeighbor().getTourLength();
        for (int i = 1; i <= graphDimension; i++) {
            for (int j = 1; j <= graphDimension; j++) {
                pheromoneTrail[i-1][j-1] = (double) numberOfAnts / nearestNeighborCost;
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
        for (int i = 1; i <= graphDimension; i++) {
            for (int j = 1; j <= graphDimension; j++) {
                updatePheromoneTrail(i,j,initialPheromoneValueACS);
            }
        }
    }

    private void setInitialPheromoneValueACS() {
        int graphDimension = graph.getNumberOfVertices();
        int nearestNeighborCost = nearestNeighbor().getTourLength();
        initialPheromoneValueACS = (double) 1 / (graphDimension * nearestNeighborCost);
    }

    private Tour nearestNeighbor() {
        List<Integer> tourList = new ArrayList<Integer>();
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
        return new Tour(graph, tourList);
    }
}
