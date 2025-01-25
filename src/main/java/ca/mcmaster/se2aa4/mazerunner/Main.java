package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.commons.cli.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.ArrayList;
 
public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");
        
        Options options = new Options();
        CommandLineParser parser = new DefaultParser();
        
        options.addOption("i", true, "Proper Input Required");

        int rows = 0;
        int columns = 0;
        int rowIndex = 0;

        //Storing maze
        char[][] mazeArray = null;

        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("i")) {
                String inputFile = cmd.getOptionValue("i");
            }

            if (args.length == 0) {
                throw new IllegalArgumentException("Please provide a maze file as a command-line argument.");
            }

            // Reading maze file
            String filePath = args[1];
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            // First pass: determine the number of rows and columns
            List<String> lines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                if (line.length() > columns) {
                    columns = line.length();  // Set columns to the longest line length
                }
                rowIndex++;
            }
             // Number of rows is the number of lines in the file
            rows = rowIndex;
            // Now, initialize the maze array with dynamic dimensions
            mazeArray = new char[rows][columns];

            // Second pass: Fill the maze array
            for (int i = 0; i < rows; i++) {
                String mazeLine = lines.get(i);
                for (int j = 0; j < mazeLine.length(); j++) {
                    mazeArray[i][j] = mazeLine.charAt(j);
                }
            }

            // Now you have a dynamically allocated mazeArray
            logger.info("Maze successfully read with dimensions: " + rows + "x" + columns);

        } 
        catch (Exception e) {
            logger.error("Error reading the maze file.", e);
        } 

        Maze maze = new Maze(mazeArray, rows, columns);
        Direction solver = new Direction(maze);

        if (maze.getRows() == 0 || maze.getColumns() == 0) {
            throw new IllegalStateException("Maze is empty or invalid.");
        }

        logger.info("**** Computing path");

        List<Character> pathFound = solver.rightHandRuleAlgorithm();
        StringBuilder pathString = new StringBuilder();
        for (Character c : pathFound) {
            pathString.append(c);  // Append each character to the string
        }
        

        if (pathFound.isEmpty()) {
            logger.warn("PATH NOT COMPUTED");
        } 
        else {
            System.out.println("Canonical Path: " + pathString.toString());
        }

        logger.info("** End of MazeRunner");
    }
}
