package tsp.aco;

import tsp.Graph;
import tsp.Tour;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This is a class represents the algorithm of Ant Colony System.
 *
 * @author Arthur Cremelie
 */

public class AntColonySystem {
    /**
     * Variable keeping the graph used in the ant colony system.
     */
    private Graph graph;
    /**
     * Variable keeping the pheromonetrails. It is a matrix with the value of the pheromone of edge (i,j) at position
     * [i][j] of the matrix.
     */
    private double[][] pheromoneTrail;
    /**
     * Constant keeping the number of ants used in the ant colony system.
     */
    private final int NUMBER_OF_ANTS = 10; //10
    /**
     * Constant keeping the alpha value of formula (1) in the paper.
     */
    private final double ALPHA = 1;
    /**
     * Constant keeping the beta value of formula (1) in the paper.
     */
    private final double BETA = 5; //2 - 5
    /**
     * Constant keeping the evaporation rate used in formula (3) in the paper.
     */
    private final double EVAPORATION_RATE = 0.1;
    /**
     * Constant keeping the evaporation rate used in formula (2) in the paper.
     */
    private final double LOCAL_EVAPORATION_RATE = 0.1;
    /**
     * Variable keeping the initial pheromone values of the system.
     */
    private double initialPheromoneValueACS;
    /**
     * Variable keeping the maximum amount of iterations.
     */
    private final int maxIterations;

    /**
     * Constructor creating an ant colony system object with the given maximum of iterations and graph. It also
     * calculates and sets the initial value of the pheromone.
     * @param maxIterations int Indicates the maximum iterations that the system should perform.
     * @param graph Graph Indicates the graph used in the ant colony system.
     */
    public AntColonySystem(int maxIterations, Graph graph) {
        this.maxIterations = maxIterations;
        this.graph = graph;
        pheromoneTrail = new double[graph.getNumberOfVertices()][graph.getNumberOfVertices()];

        setInitialPheromoneValueACS();
    }

    /**
     * Get the graph used by the system.
     * @return Graph The graph used by the system.
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * Get the pheromone value of edge (i,j)
     * @param i int Indicates the first node of the edge. It should always be a member of the vertices of the graph.
     * @param j int Indicates the last node of the edge. It should always be a member of the vertices of the graph.
     * @return double The value of pheromone of edge (i,j).
     */
    public double getPheromoneValue(int i, int j) {
        return pheromoneTrail[i-1][j-1];
    }

    /**
     * @return double The beta value of the system.
     */
    public double getBeta() {
        return BETA;
    }

    /**
     * @return double The local evaporation rate.
     */
    public double getLocalEvaporationRate() {
        return LOCAL_EVAPORATION_RATE;
    }

    /**
     * @return double The initial pheromone value.
     */
    public double getInitialPheromoneValueACS() {
        return initialPheromoneValueACS;
    }

    /**
     * Run the ant colony system algorithm.
     * @return Tour The best tour found with the ant colony system algorithm.
     */
    public Tour getBestTour() {
        return antColonySystem();
    }

    /**
     * The ant colony system algorithm as described in the book 'Ant Colony Optimization' of Marco Dorigo.
     * First we set all the pheromone values to the initial value calculated in the constructor.
     * Then we perform the ant colony system based on the maximum amount of iterations.
     * We update the pheromone values of the tour of the ant that created the best tour every iteration.
     * @return Tour The best tour found by ant colony system.
     */
    public Tour antColonySystem() {
        initializePheromoneTrailACS();
        Tour bestTour = null;
        Tour s = null;
        for (int maxI = 0; maxI < maxIterations; maxI++) {
            s = getBestAntTour();
            updatePheromoneTrail(s);
            if (bestTour == null || s.getTourLength() < bestTour.getTourLength()) {
                bestTour = s;
            }
        }
        return bestTour;
    }

    /**
     * The number of ants demanded each create a tour based on pheromone values. The ant that created the best tour
     * will get the opportunity to update it pheromone values with the pheromone evaporation rate.
     * @return Tour The tour of ant that created the best tour.
     */
    public Tour getBestAntTour() {
        Ant bestAnt = null;
        Tour bestTour = null;
        int bestTourLength = Integer.MAX_VALUE;
        for (int i = 1; i <= NUMBER_OF_ANTS; i++) {
            Ant ant = new Ant(graph, this);
            Tour antTour = ant.createTour();
            int antTourLength = antTour.getTourLength();
            if (antTourLength < bestTourLength) {
                bestAnt = ant;
                bestTour = antTour;
                bestTourLength = antTourLength;
            }
        }
        return bestTour;
    }

    /**
     * Update the pheromone trail with data from the best tour. This uses formula (3) of the paper.
     * @param bestTour The tour of the ant that produced the best tour.
     */
    private void updatePheromoneTrail(Tour bestTour) {
        double tourAntCost = bestTour.getTourLength();
        ArrayList<Integer> tourAntList = (ArrayList<Integer>) bestTour.getTour();
        for (int v = 0; v < tourAntList.size() - 1; v++){
            int i = tourAntList.get(v);
            int j = tourAntList.get(v+1);
            double value = (1 - EVAPORATION_RATE) * getPheromoneValue(i, j) + EVAPORATION_RATE * (1/tourAntCost);
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

    /**
     * Calculate and set the initial pheromone value of the ant colony system. This is based on the
     * cost of the nearest neighbour solution of the problem.
     */
    private void setInitialPheromoneValueACS() {
        int graphDimension = graph.getNumberOfVertices();
        int nearestNeighborCost = nearestNeighbor().getTourLength();
        initialPheromoneValueACS = (double) 1 / (graphDimension * nearestNeighborCost);
    }

    /**
     * Perform nearest neighbour in order to calculate the initial pheromone values. This is the same function as
     * used in Tabu Search.
     * @return Tour The tour as the result of the nearest neighbour method.
     */
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
