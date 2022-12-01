package tsp;

import tsp.aco.Ant;
import tsp.aco.AntColonySystem;
import tsp.ts.TabuSearch;

import java.io.FileNotFoundException;
import java.time.Clock;
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
        AntColonySystem antColonySystem = new AntColonySystem(maxNumberOfIterations, this);
        return antColonySystem.getBestTour();
    }

    public static void main(String[] args) throws FileNotFoundException {
        Graph burma = new Graph("burma14");
        Tour burmaTourTS = burma.getTabuSearchBestTour(1000);
        System.out.println(burmaTourTS.getTourLength());
        Tour burmaTourACS = burma.getOtherHeuristicBestTour(10);
        System.out.println(burmaTourTS.getTourLength());

        /*Graph berlin = new Graph("berlin52");
        // TS
        long start = System.nanoTime();
        Tour berlinTourTS = berlin.getTabuSearchBestTour(1000);
        long stop = System.nanoTime();
        long time = stop - start;
        double timeSeconds = (double) time / 1_000_000_000;
        System.out.println("Tabu Search result: " + berlinTourTS.getTourLength() + " in " + timeSeconds + " seconds");
        // ACS
        start = System.nanoTime();
        Tour berlinTourACS = berlin.getOtherHeuristicBestTour(1000);
        stop = System.nanoTime();
        time = stop - start;
        timeSeconds = (double) time / 1_000_000_000;
        System.out.println("Ant Colony System result: " + berlinTourACS.getTourLength() + " in " + timeSeconds + " seconds");*/

        /*Graph att = new Graph("att532");
        start = System.nanoTime();
        Tour attTour = att.getTabuSearchBestTour(100);
        stop = System.nanoTime();
        time = stop - start;
        timeSeconds = (double) time / 1_000_000_000;
        System.out.println(attTour.getTourLength() + " in " + timeSeconds + " seconds");*/

        /*Graph eil = new Graph("eil76");
        // TS
        start = System.nanoTime();
        Tour eilTourTS = eil.getTabuSearchBestTour(1000);
        stop = System.nanoTime();
        time = stop - start;
        timeSeconds = (double) time / 1_000_000_000;
        System.out.println("Tabu Search result: " + eilTourTS.getTourLength() + " in " + timeSeconds + " seconds");
        // ACS
        start = System.nanoTime();
        Tour eilTourACS = eil.getOtherHeuristicBestTour(1000);
        stop = System.nanoTime();
        time = stop - start;
        timeSeconds = (double) time / 1_000_000_000;
        System.out.println("Ant Colony System result: " + eilTourACS.getTourLength() + " in " + timeSeconds + " seconds");*/

        Graph gr = new Graph("gr21");
        // TS
        /*double start = System.nanoTime();
        Tour grTourTS = gr.getTabuSearchBestTour(1000);
        double stop = System.nanoTime();
        double time = stop - start;
        double timeSeconds = (double) time / 1_000_000_000;
        System.out.println("Tabu Search result: " + grTourTS.getTourLength() + " in " + timeSeconds + " seconds");*/

        Graph pcb = new Graph("pcb442");
        // TS
        double start = System.nanoTime();
        Tour pcbTourTS = pcb.getTabuSearchBestTour(10);
        double stop = System.nanoTime();
        double time = stop - start;
        double timeSeconds = time / 1_000_000_000;
        System.out.println("Tabu Search result: " + pcbTourTS.getTourLength() + " in " + timeSeconds + " seconds");
        // ACS
        /*double start = System.nanoTime();
        Tour pcbTourACS = pcb.getOtherHeuristicBestTour(10);
        double stop = System.nanoTime();
        double time = stop - start;
        double timeSeconds = (double) time / 1_000_000_000;
        System.out.println("Ant Colony System result: " + pcbTourACS.getTourLength() + " in " + timeSeconds + " seconds");*/
    }
}
