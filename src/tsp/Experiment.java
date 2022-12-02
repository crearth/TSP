package tsp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Experiment {
    private String problem;
    private Graph graph;
    private int maxIterationsTS;
    private int maxIterationsACS;
    private int steps;

    Experiment(String file, int maxIterationsTS, int maxIterationsACS, int steps) throws FileNotFoundException {
        problem = file;
        graph = new Graph(file);
        this.maxIterationsTS = maxIterationsTS;
        this.maxIterationsACS = maxIterationsACS;
        this.steps = steps;
    }

    public void run() {
        runTabuSearch();
        System.out.println("Tabu Search ready.");
        runAntColonySystem();
        System.out.println("Ant Colony System ready.");
    }

    private void runTabuSearch() {
        String filename = createFile("TS");
        try {
            FileWriter myWriter = new FileWriter(filename);
            for (int i = 1; i <= maxIterationsTS; i += steps) {
                double start = System.nanoTime();
                Tour tour = graph.getTabuSearchBestTour(i);
                double stop = System.nanoTime();
                double time = stop - start;
                double timeSeconds = time / 1_000_000_000;
                myWriter.write(i + ", " + timeSeconds + ", " + tour.getTourLength());
                myWriter.write(String.format("%n"));
            }
            myWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runAntColonySystem() {
        String filename = createFile("ACS");
        try {
            FileWriter myWriter = new FileWriter(filename);
            for (int i = 1; i <= maxIterationsACS; i += steps) {
                double start = System.nanoTime();
                Tour tour = graph.getOtherHeuristicBestTour(i);
                double stop = System.nanoTime();
                double time = stop - start;
                double timeSeconds = time / 1_000_000_000;
                myWriter.write(i + ", " + timeSeconds + ", " + tour.getTourLength());
                myWriter.write(String.format("%n"));
            }
            myWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createFile(String heuristic) {
        String filename = null;
        if (heuristic.equals("TS")) {
            filename = "experiments/" + problem + "_" + heuristic + "_" + maxIterationsTS + ".txt";
        } else if (heuristic.equals("ACS")) {
            filename = "experiments/" + problem + "_" + heuristic + "_" + maxIterationsACS + ".txt";
        }
        try {
            assert filename != null;
            File file = new File(filename);
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Experiment berlin = new Experiment("berlin52", 1000, 100, 5);
        berlin.run();
        Experiment burma = new Experiment("burma14", 1000, 100,2);
        burma.run();
        Experiment pcb = new Experiment("pcb442", 1000,100, 2);
        pcb.run();
    }
}
