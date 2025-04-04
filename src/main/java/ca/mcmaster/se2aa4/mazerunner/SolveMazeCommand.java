package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SolveMazeCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SolveMazeCommand.class);
    private final Direction solver;

    public SolveMazeCommand(Direction solver) {
        this.solver = solver;
    }

    @Override
    public void execute() {
        logger.info("**** Computing path");
        List<Character> pathFound = solver.rightHandRuleAlgorithm();
        if (pathFound.isEmpty()) {
            logger.warn("PATH NOT COMPUTED");
        } else {
            StringBuilder canonicalPath = new StringBuilder();
            for (Character c : pathFound) {
                canonicalPath.append(c);
            }
            System.out.println("Canonical Path: " + canonicalPath);
            System.out.println("Factorized Path: " + solver.toFactorizedPath(pathFound));
        }
    }
}
