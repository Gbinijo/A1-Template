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

        MazeCLI cli = new MazeCLI(args);
        String filePath = cli.getInputFile();
        String pathToVerify = cli.getPathToVerify();

        if (filePath == null) {
            logger.error("No maze file provided.");
            return;
        }

        try {
            Maze maze = MazeFileReader.readMaze(filePath);
            Direction solver = new Direction(maze);

            if (pathToVerify != null) {
                boolean isValid = solver.verifyPath(pathToVerify);
                if (isValid) {
                    System.out.println("Path verification result: Valid");
                } 
                else {
                    System.out.println("Path verification result: Invalid");
                }
            } 
            else {
                logger.info("**** Computing path");
                List<Character> pathFound = solver.rightHandRuleAlgorithm();
                if (pathFound.isEmpty()) {
                    logger.warn("PATH NOT COMPUTED");
                } 
                else {
                    StringBuilder canonicalPath = new StringBuilder();
                    for (Character c : pathFound) {
                        canonicalPath.append(c);
                    }
                    System.out.println("Canonical Path: " + canonicalPath.toString());
                    System.out.println("Factorized Path: " + solver.toFactorizedPath(pathFound));
                }
            }
        } catch (Exception e) {
            logger.error("Error processing maze file.", e);
        }

        logger.info("** End of MazeRunner");
    }
}
