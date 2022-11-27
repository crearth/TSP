package tsp.aco;

import tsp.Graph;
import tsp.Tour;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Ant {
    private AntColonySystem system;
    private Graph graph;
    private Tour antTour;

    private ArrayList<Integer> unvisitedVertices;

    public Ant(Graph graph, AntColonySystem system) {
        this.system = system;
        this.graph = system.getGraph();
        unvisitedVertices = new ArrayList<Integer>(graph.getVertices());
    }

    /**
     * Create an ant tour.
     * @return Tour The tour created by the ant.
     */
    public Tour createTour() {
        ArrayList<Integer> tourList = new ArrayList<Integer>();
        int current = getRandomVertex();
        unvisitedVertices.remove((Integer) current);
        tourList.add(current);
        int bestVertex = 0;
        double q0 = 0.9; // CONSTANT TO CHOSE OPTIMALLY //TODO place it somewhere else
        double beta = system.getBeta();
        while(unvisitedVertices.size() > 0) {
            double bestPheromone = Integer.MIN_VALUE;
            double q = getRandomDouble();
            double argSum = 0;
            for (int i : unvisitedVertices) {
                double pheromoneValue = system.getPheromoneValue(current, i);
                double heuristicValue = (double) 1 / graph.getDistance(current, i);
                double value = pheromoneValue * Math.pow(heuristicValue, beta);
                argSum += value;
            }
            if (q <= q0) {
                for (int i : unvisitedVertices) {
                    double pheromoneValue = system.getPheromoneValue(current, i);
                    double heuristicValue = (double) 1 / graph.getDistance(current, i);
                    double arg = pheromoneValue * Math.pow(heuristicValue, beta);
                    if (arg > bestPheromone) {
                        bestPheromone = arg;
                        bestVertex = i;
                    }
                }
            } else {
                for (int i : unvisitedVertices) {
                    double pheromoneValue = system.getPheromoneValue(current, i);
                    double heuristicValue = (double) 1 / graph.getDistance(current, i);
                    double arg = (pheromoneValue * Math.pow(heuristicValue, beta)) / argSum;
                    if (arg > bestPheromone) {
                        bestPheromone = arg;
                        bestVertex = i;
                    }
                }
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

    private void localPheromoneTrailUpdate(int i, int j) {
        double initialPheromoneValue = system.getInitialPheromoneValueACS();
        double value = (1 - system.getLocalEvaporationRate()) * system.getPheromoneValue(i, j) + system.getLocalEvaporationRate() * initialPheromoneValue;
        system.updatePheromoneTrail(i, j, value);
    }

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

    private double getRandomDouble() {
        return ThreadLocalRandom.current().nextDouble(0,1);
    }

}
