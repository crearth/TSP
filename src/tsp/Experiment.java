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

    public void runTabuSearchByIteration(int iterations) {
        int tourLengthSum = 0;
        double timeSum = 0;
        for (int j = 1; j <= 20; j++) {
            double start = System.nanoTime();
            Tour tour = graph.getTabuSearchBestTour(iterations);
            double stop = System.nanoTime();
            double time = stop - start;
            timeSum += time / 1_000_000_000;
            tourLengthSum += tour.getTourLength();
        }
        System.out.println(problem + ", " + timeSum/20 + ", " + tourLengthSum/20);
    }

    public void runAntColonySystem() {
        String filename = createFile("ACS");
        try {
            FileWriter myWriter = new FileWriter(filename);
            for (int i = 1; i <= maxIterationsACS; i += steps) {
                System.out.println(problem + ": Ant Colony iteraties: " + i);
                int tourLengthSum = 0;
                double timeSum = 0;
                for (int j = 1; j <= 10; j++) {
                    double start = System.nanoTime();
                    Tour tour = graph.getOtherHeuristicBestTour(i);
                    double stop = System.nanoTime();
                    double time = stop - start;
                    timeSum += time / 1_000_000_000;
                    tourLengthSum += tour.getTourLength();
                }
                myWriter.write(i + ", " + timeSum/10 + ", " + tourLengthSum/10);
                myWriter.write(String.format("%n"));
            }
            myWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runAntColonySystemByIteration(int iterations) {
        int tourLengthSum = 0;
        double timeSum = 0;
        for (int j = 1; j <= 10; j++) {
            double start = System.nanoTime();
            Tour tour = graph.getOtherHeuristicBestTour(iterations);
            double stop = System.nanoTime();
            double time = stop - start;
            timeSum += time / 1_000_000_000;
            tourLengthSum += tour.getTourLength();
        }
        System.out.println(problem + ", " + timeSum/10 + ", " + tourLengthSum/10);
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
        Experiment eil51 = new Experiment("eil51", 200, 50, 2);
        eil51.runTabuSearchByIteration(50);

        Experiment berlin = new Experiment("berlin52", 200, 50, 2);
        berlin.runAntColonySystemByIteration(50);

        Experiment kroA = new Experiment("kroA100", 200, 50, 2);
        kroA.runAntColonySystemByIteration(50);

        Experiment eil101 = new Experiment("eil101", 200, 50, 2);
        eil101.runAntColonySystemByIteration(50);

        Experiment pr107 = new Experiment("pr107", 200, 50, 2);
        pr107.runAntColonySystemByIteration(50);

        Experiment pr124 = new Experiment("pr124", 200, 50, 2);
        pr124.runAntColonySystemByIteration(50);

    }
}
