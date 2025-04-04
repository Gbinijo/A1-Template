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
            InputReader reader = new MazeFileReader();
            Maze maze = reader.readMaze(filePath);
            Direction solver = new Direction(maze);

            Command command;

            if (pathToVerify != null) {
                command = new VerifyPathCommand(solver, pathToVerify);
            } else {
                command = new SolveMazeCommand(solver);
            }

            command.execute();

        } catch (Exception e) {
            logger.error("Error processing maze file.", e);
        }

        logger.info("** End of MazeRunner");
    }
}