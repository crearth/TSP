package tsp;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;

public class Data {

    private final String filename;
    private final File file;
    private final ArrayList<String> content;
    public String name = null;
    public String type = null;
    public int dimension = 0;
    public String edgeWeightType = null;
    public HashMap<Integer, ArrayList<Double>> dataPoints = new HashMap<>();


    public Data(String tspProblem) throws FileNotFoundException {
        filename = tspProblem;
        file = getFile();
        content = transform();
        readSpecification();
        parse();
    }

    /**
     * get the file from the data folder with the given name
     * @return file
     */
    public File getFile() {
        String fullFilename = "data/" + filename + ".tsp";
        File file = new File(fullFilename);
        return file;
    }

    /**
     * parse the file to an ArrayList with a new element for each line
     * @return fileContent
     * @throws FileNotFoundException if the file is not found
     */
    public ArrayList<String> transform() throws FileNotFoundException {
        Scanner reader = new Scanner(file);
        ArrayList<String> fileContent = new ArrayList<String>();
        while (reader.hasNextLine()) {
            fileContent.add(reader.nextLine());
        }
        reader.close();
        return fileContent;
    }

    /**
     * set the specifications of the file
     */
    public void readSpecification() {
        name = content.get(0).split(": ")[1];
        type = content.get(1).split(": ")[1];
        dimension = Integer.parseInt(content.get(3).split(": ")[1]);
        edgeWeightType = content.get(4).split(": ")[1];
    }

    /**
     * get the data points for the EUC_2D problem
     * @return dataPoints
     */
    public HashMap<Integer, ArrayList<Double>> getDataPoints() {
        List<String> data = content.subList(6, content.size());
        for (String i : data) {
            if (!i.equals("EOF")) {
                // trim to remove whitespaces before and after line content
                String[] line = i.trim().split("\\s+");
                Double x = Double.valueOf(line[1]);
                Double y = Double.valueOf(line[2]);
                ArrayList<Double> point = new ArrayList<Double>();
                point.add(x);
                point.add(y);
                dataPoints.put(Integer.valueOf(line[0]), point);
            } else {
                // we have to return or break the function, because in some files the last line after "EOF"
                // is a blank line
                return dataPoints;
            }
        }
        return dataPoints;
    }

    public int[][] parse() {
        int[][] distanceMatrix = new int[dimension-1][dimension-1];

        if (edgeWeightType.equals("EUC_2D")) {
            dataPoints = getDataPoints();
            for (int i = 1; i < dimension; i++) {
                for (int j = 1; j < dimension; j++) {
                    double xd = dataPoints.get(i).get(0) - dataPoints.get(j).get(0);
                    double yd = dataPoints.get(i).get(1) - dataPoints.get(j).get(1);
                    int dij = (int) Math.round(Math.sqrt(xd*xd + yd*yd));
                    distanceMatrix[i-1][j-1] = dij;
                }
            }
        }
        System.out.println(Arrays.deepToString(distanceMatrix));
        return distanceMatrix;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Data berlin = new Data("berlin52");

        Data bier = new Data("bier127");

        //Data lin = new Data("lin318");

        //Data brazil = new Data("brazil58");
    }
}

