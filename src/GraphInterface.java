package be.KULeuven;

import java.util.Collection;

public interface GraphInterface {

    /**
     *  Returns a collection of integers representing the vertices of the graph.
     */
    Collection<Integer> getVertices();

    /**
     *  Returns the number of nodes present in the graph.
     */
    int getNumberOfVertices();

    /**
     * Returns the distance between node i and node j
     * @param i indicates a node of the graph. It should be one of the entries in the collection getVertices().
     * @param j indicates a node of the graph. It should be one of the entries in the collection getVertices().
     */
    int getDistance(int i, int j);

    /**
     * Returns the best tour (of shortest length) found by your Tabu Search algorithm.
     * @param maxNumberOfIterations indicates the maximum number of iterations your algorithm is allowed to perform.
     */
    Tour getTabuSearchBestTour(int maxNumberOfIterations);

    /**
     * Returns the best tour (of shortest length) found by another chosen metaheuristic. It should be either a Genetic
     * Algorithm, Simulated Annealing or Ant Colony Optimization.
     * @param maxNumberOfIterations indicated the maximum number of iterations your algorithm is allowed to perform.
     */
    Tour getOtherHeuristicBestTour(int maxNumberOfIterations);


}
