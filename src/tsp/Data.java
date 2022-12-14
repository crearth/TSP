package tsp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This is a class represents the used data for graphs.
 *
 * @author Arthur Cremelie
 */

public class Data {

    /**
     * The name of the file, excluding the .tsp extension.
     */
    private final String filename;
    /**
     * The file object.
     */
    private File file;
    /**
     * An ArrayList that contains all the content of the file.
     */
    private ArrayList<String> content;
    /**
     * The name of the problem.
     */
    public String name = null;
    /**
     * The type of the problem. In this case this will always be TSP.
     */
    public String type = null;
    /**
     * The dimension (number of vertices) of the problem.
     */
    public int dimension = 0;
    /**
     * The type of edge weights.
     */
    public String edgeWeightType = null;
    /**
     * The format of the edge weights (only for matrices).
     */
    public String edgeWeightFormat = null;
    /**
     * The data points in x,y coordinates and with a number.
     */
    public HashMap<Integer, ArrayList<Double>> dataPoints = new HashMap<>();
    /**
     * A matrix with the distances between vertices i and j.
     */
    public int[][] distanceMatrix;


    public Data(String tspProblem) throws FileNotFoundException {
        filename = tspProblem;
        setFile();
        setContent();
        readSpecification();
        distanceMatrix = parse();
    }

    public HashMap<Integer, ArrayList<Double>> getDataPoints() {
        return dataPoints;
    }

    public int getDimension() {
        return dimension;
    }

    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    /**
     * set the file from the data folder with the given name
     */
    public void setFile() {
        String fullFilename = "data/" + filename + ".tsp";
        file = new File(fullFilename);
    }

    /**
     * parse the file to an ArrayList with a new element for each line
     * @throws FileNotFoundException if the file is not found
     */
    public void setContent() throws FileNotFoundException {
        Scanner reader = new Scanner(file);
        ArrayList<String> fileContent = new ArrayList<String>();
        while (reader.hasNextLine()) {
            fileContent.add(reader.nextLine());
        }
        reader.close();
        content = fileContent;
    }

    /**
     * set the specifications of the file
     */
    public void readSpecification() {
        name = content.get(0).split(": ")[1];
        type = content.get(1).split(": ")[1];
        if (content.get(2).split(": ")[0].equals("DIMENSION")) {
            dimension = Integer.parseInt(content.get(2).split(": ")[1]);
            edgeWeightType = content.get(3).split(": ")[1];
            if (edgeWeightType.equals("EXPLICIT")) {
                edgeWeightFormat = content.get(4).split(": ")[1].trim();
            }
        } else {
            dimension = Integer.parseInt(content.get(3).split(": ")[1]);
            edgeWeightType = content.get(4).split(": ")[1];
            if (edgeWeightType.equals("EXPLICIT")) {
                edgeWeightFormat = content.get(5).split(": ")[1].trim();
            }
        }
    }

    /**
     * Get the index of the first line that starts with an integer.
     * @return integer start
     */
    private int getStartIndex() {
        for (int i = 0; i < content.size(); i++) {
            // regex 1 of meer digits
            if (content.get(i).trim().substring(0,1).matches("\\d+")) {
                return i;
            }
        }
        return 0;
    }

    /**
     * get the data points for the EUC_2D, ATT,GEO problem
     * @return dataPoints
     */
    public HashMap<Integer, ArrayList<Double>> parseDataPoints() {
        int start = getStartIndex();
        List<String> data = content.subList(start, content.size());
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

    /**
     * get the data points for the matrix problems
     * @return dataPoints
     */
    public ArrayList<Integer> parseDataPointsMatrix() {
        int start = getStartIndex();
        List<String> data = content.subList(start, content.size());
        ArrayList<Integer> matrixElements = new ArrayList<>();
        for (String i : data) {
            if (!i.equals("EOF")) {
                String[] lineSplit = i.trim().split("\\s+");
                for (String j : lineSplit) {
                    if (!j.trim().equals("EOF")) {
                        matrixElements.add(Integer.valueOf(j.trim()));
                    } else {
                        return matrixElements;
                    }
                }
            } else {
                // we have to return or break the function, because in some files the last line after "EOF"
                // is a blank line
                return matrixElements;
            }
        }
        return matrixElements;
    }

    public int[][] parse() {
        int[][] distanceMatrix = switch (edgeWeightType) {
            case "EUC_2D" -> parseEUC2D();
            case "GEO" -> parseGEO();
            case "ATT" -> parseATT();
            case "EXPLICIT" -> parseMatrix();
            default -> null;
        };
        return distanceMatrix;
    }

    /**
     * EUC_2D-parser
     * @return a matrix with the distances from and to all vertices
     */
    public int[][] parseEUC2D() {
        int[][] distanceMatrix = new int[dimension][dimension];
        dataPoints = parseDataPoints();
        for (int i = 1; i <= dimension; i++) {
            for (int j = 1; j <= dimension; j++) {
                double xd = dataPoints.get(i).get(0) - dataPoints.get(j).get(0);
                double yd = dataPoints.get(i).get(1) - dataPoints.get(j).get(1);
                int dij = (int) Math.round(Math.sqrt(xd*xd + yd*yd));
                distanceMatrix[i-1][j-1] = dij;
            }
        }
        return distanceMatrix;
    }

    /**
     * GEO-parser
     * @return a matrix with the distances from and to all vertices
     */
    public int[][] parseGEO() {
        int[][] distanceMatrix = new int[dimension][dimension];
        dataPoints = parseDataPoints();
        for (int i = 1; i <= dimension; i++) {
            for (int j = 1; j <= dimension; j++) {
                double[] iCoordinates = calculateCoordinates(dataPoints.get(i).get(0), dataPoints.get(i).get(1));
                double[] jCoordinates = calculateCoordinates(dataPoints.get(j).get(0), dataPoints.get(j).get(1));
                double RRR = 6378.388;
                double q1 = Math.cos(iCoordinates[1] - jCoordinates[1]);
                double q2 = Math.cos(iCoordinates[0] - jCoordinates[0]);
                double q3 = Math.cos(iCoordinates[0] + jCoordinates[0]);
                int dij = (int) ( RRR * Math.acos( 0.5*((1.0+q1)*q2 - (1.0-q1)*q3) ) + 1.0);
                distanceMatrix[i-1][j-1] = dij;
            }
        }
        return distanceMatrix;
    }

    /**
     * ATT-parser
     * @return a matrix with the distances from and to all vertices
     */
    public int[][] parseATT() {
        int[][] distanceMatrix = new int[dimension][dimension];
        dataPoints = parseDataPoints();
        for (int i = 1; i <= dimension; i++) {
            for (int j = 1; j <= dimension; j++) {
                double xd = dataPoints.get(i).get(0) - dataPoints.get(j).get(0);
                double yd = dataPoints.get(i).get(1) - dataPoints.get(j).get(1);
                double rij = Math.sqrt((xd*xd + yd*yd) / 10.0);
                int tij = (int) Math.round(rij);
                int dij = 0;
                if (tij < rij) {
                    dij = tij + 1;
                } else {
                    dij = tij;
                }
                distanceMatrix[i-1][j-1] = dij;
            }
        }
        return distanceMatrix;
    }

    public int[][] parseMatrix() {
        return switch (edgeWeightFormat) {
            case "LOWER_DIAG_ROW", "UPPER_DIAG_COL" -> parseLowerDiagRow();
            case "UPPER_DIAG_ROW", "LOWER_DIAG_COL" -> parseUpperDiagRow();
            case "LOWER_ROW", "UPPER_COL" -> parseLowerRow();
            case "UPPER_ROW", "LOWER_COL" -> parseUpperRow();
            case "FULL_MATRIX" -> parseFullMatrix();
            default -> null;
        };
    }

    private int[][] parseFullMatrix() {
        int[][] distanceMatrix = new int[dimension][dimension];
        ArrayList<Integer> elements = parseDataPointsMatrix();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                distanceMatrix[i][j] = elements.get(i * dimension + j);
            }
        }
        return distanceMatrix;
    }

    private int[][] parseUpperRow() {
        int[][] distanceMatrix = new int[dimension][dimension];
        ArrayList<Integer> elements = parseDataPointsMatrix();
        int counter = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = i + 1; j < dimension; j++) {
                distanceMatrix[i][j] = elements.get(counter);
                distanceMatrix[j][i] = elements.get(counter);
                counter++;
            }
        }
        return distanceMatrix;
    }

    private int[][] parseLowerRow() {
        int[][] distanceMatrix = new int[dimension][dimension];
        ArrayList<Integer> elements = parseDataPointsMatrix();
        int counter = 0;
        for (int i = 1; i < dimension; i++) {
            for (int j = 0; j < i; j++) {
                distanceMatrix[i][j] = elements.get(counter);
                distanceMatrix[j][i] = elements.get(counter);
                counter++;
            }
        }
        return distanceMatrix;
    }

    private int[][] parseUpperDiagRow() {
        int[][] distanceMatrix = new int[dimension][dimension];
        ArrayList<Integer> elements = parseDataPointsMatrix();
        int counter = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = i; j < dimension; j++) {
                if (j != i) {
                    distanceMatrix[i][j] = elements.get(counter);
                    distanceMatrix[j][i] = elements.get(counter);
                }
                counter++;
            }
        }
        return distanceMatrix;
    }

    private int[][] parseLowerDiagRow() {
        int[][] distanceMatrix = new int[dimension][dimension];
        ArrayList<Integer> elements = parseDataPointsMatrix();
        int counter = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < i + 1; j++) {
                if (j != i) {
                    distanceMatrix[i][j] = elements.get(counter);
                    distanceMatrix[j][i] = elements.get(counter);
                }
                counter++;
            }
        }
        return distanceMatrix;
    }

    /**
     * Help function to calculate the longitude and latitude for the GEO-parser
     * @param x the x coordinate
     * @param y the y coordinate
     * @return a list of two elements, the longitude and the latitude
     */
    private double[] calculateCoordinates(double x, double y) {
        double[] coordinates = new double[2];
        int deg = (int) Math.round(x);
        double min = x - deg;
        coordinates[0] = Math.PI * (deg + 5.0 * min / 3.0) / 180.0;
        deg = (int) Math.round(y);
        min = y - deg;
        coordinates[1] = Math.PI * (deg + 5.0 * min / 3.0) / 180.0;
        return coordinates;
    }
}

