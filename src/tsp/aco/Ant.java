package tsp.aco;

import tsp.Graph;
import tsp.Tour;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Ant {
    /**
     * Keeping the Ant Colony System in which the ant behaves.
     */
    private AntColonySystem system;
    /**
     * Keeping the graph of the problem.
     */
    private Graph graph;
    /**
     * The tour that belongs to this ant.
     */
    private Tour antTour;
    /**
     * The list keeping the vertices that are not yet visited.
     */
    private ArrayList<Integer> unvisitedVertices;

    /**
     * Constructor for an Ant object. It will set the system and graph of the ant to the given system and graph.
     * It initializes the unvisitedVertices list to copy of all the vertices of the given graph.
     * @param graph Graph
     * @param system AntColonySystem
     */
    public Ant(Graph graph, AntColonySystem system) {
        this.system = system;
        this.graph = system.getGraph();
        unvisitedVertices = new ArrayList<Integer>(graph.getVertices());
    }

    /**
     * Create an ant tour. Start with an empty ArrayList, a random vertex and remove the random vertex from the
     * unvisitedVertices list. While there are vertices that are not visited, look for the best vertex based on two
     * different calculations. The calculation is chosen based on a random uniformly distributed variable between
     * 0 and 1: q. If q is less or equal to q0, do the calculation from case1, else do the calculation from case2.
     * After a bestVertex is found, let the pheromonetrail of edge (current, bestVertex) update, add the vertex
     * to the tour and remove the vertex from the unvisitedVertices.
     * @return Tour The tour created by the ant.
     */
    public Tour createTour() {
        ArrayList<Integer> tourList = new ArrayList<Integer>();
        int current = getRandomVertex();
        unvisitedVertices.remove((Integer) current);
        tourList.add(current);
        int bestVertex = 0;
        double q0 = 0.9;
        double beta = system.getBeta();
        while(unvisitedVertices.size() > 0) {
            double bestPheromone = Integer.MIN_VALUE;
            double q = getRandomDouble();
            if (q <= q0) {
                bestVertex = getBestVertexCase1(current, bestVertex, beta, bestPheromone);
            } else {
                bestVertex = getBestVertexCase2(current, bestVertex, beta, bestPheromone);
            }
            if (tourList.size() > 1) {
                localPheromoneTrailUpdate(current, bestVertex);
            }
            current = bestVertex;
            unvisitedVertices.remove((Integer) current);
            tourList.add(current);
        }
        antTour = new Tour(graph, tourList);
        return antTour;
    }

    /**
     * If q <= q0. Searches the best vertex to take based on formula (1) in the paper.
     * @return int The best vertex.
     */
    private int getBestVertexCase1(int current, int bestVertex, double beta, double bestPheromone) {
        for (int i : unvisitedVertices) {
            double pheromoneValue = system.getPheromoneValue(current, i);
            double heuristicValue = (double) 1 / graph.getDistance(current, i);
            double arg = pheromoneValue * Math.pow(heuristicValue, beta);
            if (arg > bestPheromone) {
                bestPheromone = arg;
                bestVertex = i;
            }
        }
        return bestVertex;
    }

    /**
     * If q > q0. Searches the best vertex to take based on formula (1) in the paper.
     * @return int The best vertex.
     */
    private int getBestVertexCase2(int current, int bestVertex, double beta, double bestPheromone) {
        double argSum = getArgSum(current, beta);
        for (int i : unvisitedVertices) {
            double pheromoneValue = system.getPheromoneValue(current, i);
            double heuristicValue = (double) 1 / graph.getDistance(current, i);
            double arg = (pheromoneValue * Math.pow(heuristicValue, beta)) / argSum;
            if (arg > bestPheromone) {
                bestPheromone = arg;
                bestVertex = i;
            }
        }
        return bestVertex;
    }

    /**
     * Calculate the variable needed in the calculation of case2. (see formula (1) in the paper)
     * @param current int The current vertex.
     * @param beta int The BETA value.
     * @return argSum The sum of (pheromonevalues^1 times (1/d_ij)^beta)
     */
    private double getArgSum(int current, double beta) {
        double argSum = 0;
        for (int i : unvisitedVertices) {
            double pheromoneValue = system.getPheromoneValue(current, i);
            double heuristicValue = (double) 1 / graph.getDistance(current, i);
            double value = pheromoneValue * Math.pow(heuristicValue, beta);
            argSum += value;
        }
        return argSum;
    }

    /**
     * Update the pheromone trail of the edge just added with an evaporation rate. (see formula (2) in the paper)
     * @param i int Vertex i.
     * @param j int Vertex j.
     */
    private void localPheromoneTrailUpdate(int i, int j) {
        double initialPheromoneValue = system.getInitialPheromoneValueACS();
        double value = (1 - system.getLocalEvaporationRate()) * system.getPheromoneValue(i, j)
                + system.getLocalEvaporationRate() * initialPheromoneValue;
        system.updatePheromoneTrail(i, j, value);
    }

    /**
     * Get the tour that the ant created.
     * @return Tour The tour that the ant created.
     */
    public Tour getTour() {
        return antTour;
    }

    /**
     * Get a random vertex.
     * @return int The random vertex.
     */
    private int getRandomVertex() {
        return ThreadLocalRandom.current().nextInt(1, graph.getNumberOfVertices() + 1);
    }

    /**
     * Get a random double uniformly distributed between 0 and 1.
     * @return int The random double.
     */
    private double getRandomDouble() {
        return ThreadLocalRandom.current().nextDouble(0,1);
    }

}
