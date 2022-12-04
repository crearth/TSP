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

    public void runTabuSearch() {
        String filename = createFile("TS");
        try {
            FileWriter myWriter = new FileWriter(filename);
            for (int i = 1; i <= maxIterationsTS; i += steps) {
                System.out.println("Tabu Search iteraties: " + i);
                int tourLengthSum = 0;
                double timeSum = 0;
                for (int j = 1; j <= 20; j++) {
                    System.out.println("Tabu Search ronde: " + j);
                    double start = System.nanoTime();
                    Tour tour = graph.getTabuSearchBestTour(i);
                    double stop = System.nanoTime();
                    double time = stop - start;
                    timeSum += time / 1_000_000_000;
                    tourLengthSum += tour.getTourLength();
                }
                myWriter.write(i + ", " + timeSum/20 + ", " + tourLengthSum/20);
                myWriter.write(String.format("%n"));
            }
            myWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runAntColonySystem() {
        String filename = createFile("ACS");
        try {
            FileWriter myWriter = new FileWriter(filename);
            for (int i = 1; i <= maxIterationsACS; i += steps) {
                System.out.println("Ant Colony iteraties: " + i);
                int tourLengthSum = 0;
                double timeSum = 0;
                for (int j = 1; j <= 20; j++) {
                    System.out.println("Ant Colony ronde: " + j);
                    double start = System.nanoTime();
                    Tour tour = graph.getOtherHeuristicBestTour(i);
                    double stop = System.nanoTime();
                    double time = stop - start;
                    timeSum += time / 1_000_000_000;
                    tourLengthSum += tour.getTourLength();
                }
                myWriter.write(i + ", " + timeSum/20 + ", " + tourLengthSum/20);
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
        Experiment berlin = new Experiment("berlin52", 500, 50, 5);
        berlin.runTabuSearch();
        berlin.runAntColonySystem();
        //berlin.run();
        Experiment burma = new Experiment("burma14", 1000, 100,2);
        //burma.run();
        Experiment pcb = new Experiment("pcb442", 500,50, 2);
        //pcb.run();
    }
}
