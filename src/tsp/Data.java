package tsp;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

public class Data {

    private final String filename;
    private final File file;

    public Data(String tspProblem) {
        filename = tspProblem;
        file = getFile();
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
        System.out.println(fileContent);
        return fileContent;
    }


    public static void main(String[] args) throws FileNotFoundException {
        Data berlin = new Data("berlin52");
        ArrayList<String> berlinData = berlin.readFile();
    }
}

