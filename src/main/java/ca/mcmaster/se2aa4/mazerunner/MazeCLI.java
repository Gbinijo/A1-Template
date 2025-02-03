package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.commons.cli.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.ArrayList;

public class MazeCLI implements CLIHandler {
    private String inputFile;
    private String pathToVerify;

    public MazeCLI(String[] args) {
        Options options = new Options();
        CommandLineParser parser = new DefaultParser();

        options.addOption("i", true, "Proper Input Required");
        options.addOption("p", true, "Path Verification Required");

        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("i")) {
                inputFile = cmd.getOptionValue("i");
            }
            if (cmd.hasOption("p")) {
                pathToVerify = cmd.getOptionValue("p");
            }
        } catch (ParseException e) {
            System.err.println("Error parsing command line arguments: " + e.getMessage());
        }
    }

    @Override
    public String getInputFile() {
        return inputFile;
    }

    @Override
    public String getPathToVerify() {
        return pathToVerify;
    }
}