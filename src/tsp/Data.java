package tsp;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner; // Import the Scanner class to read text files

public class Data {

    private final String filename;
    private final File file;
    private final ArrayList<String> content;
    private String name;
    private String type;
    private String comment;
    private int dimension;
    private String edgeWeightType;

    public Data(String tspProblem) throws FileNotFoundException {
        filename = tspProblem;
        file = getFile();
        content = readFile();
        readSpecification();
    }

    public File getFile() {
        String fullFilename = "data/" + filename + ".tsp";
        File file = new File(fullFilename);
        return file;
    }

    public ArrayList<String> readFile() throws FileNotFoundException {
        Scanner reader = new Scanner(file);
        ArrayList<String> fileContent = new ArrayList<String>();
        while (reader.hasNextLine()) {
            fileContent.add(reader.nextLine());
        }
        reader.close();
        return fileContent;
    }

    public void readSpecification() {
        name = content.get(0).split(" ")[1];
        type = content.get(1).split(" ")[1];
        comment = content.get(2).split(" ")[1];
        dimension = Integer.parseInt(content.get(3).split(" ")[1]);
        edgeWeightType = content.get(3).split(" ")[1];
    }


    public static void main(String[] args) throws FileNotFoundException {
        Data berlin = new Data("berlin52");
        ArrayList<String> berlinData = berlin.readFile();
        System.out.println(berlin.readFile());
    }
}

