package tsp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Experiment {
    private String filename;
    private Graph graph;
    private int maxIterations;
    private int steps;

    Experiment(String file, int maxIterations, int steps) throws FileNotFoundException {
        filename = file;
        graph = new Graph(file);
        this.maxIterations = maxIterations;
        this.steps = steps;
    }

    public void run() {
        createFile();
        try {
            FileWriter myWriter = new FileWriter("experiments/" + filename + "_" + maxIterations + ".txt");
            for (int i = 1; i <= maxIterations; i += steps) {
                Tour tour = graph.getOtherHeuristicBestTour(i);
                myWriter.write(i + ", " + tour.getTourLength());
                myWriter.write(String.format("%n"));
            }
            myWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createFile() {
        try {
            File file = new File("experiments/" + filename + "_" + maxIterations + ".txt");
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Experiment berlin = new Experiment("berlin52", 100, 2);
        berlin.run();
        Experiment burma = new Experiment("burma14", 100, 2);
        burma.run();
    }
}
