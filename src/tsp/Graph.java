package tsp;

import tsp.aco.AntColonySystem;
import tsp.ts.TabuSearch;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This is a class represents a graph. It implements the class GraphInteface.
 *
 * @author Arthur Cremelie
 */

public class Graph implements GraphInterface{

    /**
     * Variable keeping the data of the class.
     */
    private final Data data;

    /**
     * Constructor for the graph object. It creates a new graph object given the string of a TSP problem.
     * @param tspProblem String The name of the TSP problem without the '.tsp' extension.
     * @throws FileNotFoundException If the file is not found.
     */
    public Graph(String tspProblem) throws FileNotFoundException {
        data = new Data(tspProblem);
    }

    /**
     * Get the list of vertices used in this problem.
     * @return Collection A list of integers that represent the vertices of the problem.
     */
    @Override
    public Collection<Integer> getVertices() {
        Collection<Integer> vertices = new ArrayList<>();
        for(int i = 1; i <= data.getDimension(); i++){
            vertices.add(i);
        }
        return vertices;
    }

    /**
     * Get the number of vertices. This is also known as the dimension of the problem.
     * @return int The dimension of the problem.
     */
    @Override
    public int getNumberOfVertices() {
        return data.getDimension();
    }

    /**
     * Get the distance between node i and j.
     * @param i indicates a node of the graph. It should be one of the entries in the collection getVertices().
     * @param j indicates a node of the graph. It should be one of the entries in the collection getVertices().
     * @return the distance between node i and j.
     */
    @Override
    public int getDistance(int i, int j) {
        return data.getDistanceMatrix()[i-1][j-1];
    }

    /**
     * Get the best tour with the Tabu Search algorithm, given an amount of iterations.
     * @param maxNumberOfIterations indicates the maximum number of iterations your algorithm is allowed to perform.
     * @return Tour The best tour found.
     */
    @Override
    public Tour getTabuSearchBestTour(int maxNumberOfIterations) {
        TabuSearch tabuSearch = new TabuSearch(maxNumberOfIterations, this);
        return tabuSearch.getBestTour();
    }

    /**
     * Get the best tour with the Ant Colony System algorithm, given an amount of iterations.
     * @param maxNumberOfIterations indicated the maximum number of iterations your algorithm is allowed to perform.
     * @return Tour The best tour found.
     */
    @Override
    public Tour getOtherHeuristicBestTour(int maxNumberOfIterations) {
        AntColonySystem antColonySystem = new AntColonySystem(maxNumberOfIterations, this);
        return antColonySystem.getBestTour();
    }
}
