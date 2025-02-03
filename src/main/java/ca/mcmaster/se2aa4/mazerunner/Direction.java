package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Direction {
    private Maze maze;
    private MazeNavigator navigator;
    private MovementHandler movementHandler;
    private static final Logger logger = LogManager.getLogger(Direction.class);

    public Direction(Maze maze) {
        this.maze = maze;
        this.navigator = new MazeNavigator(maze);
        this.movementHandler = new MovementHandler(maze);
    }

    public List<Character> rightHandRuleAlgorithm() {
        return navigator.rightHandRuleAlgorithm();
    }

    public boolean verifyPath(String path) {
        return navigator.verifyPath(path);
    }

    public String toFactorizedPath(List<Character> canonicalPath) {
        return navigator.toFactorizedPath(canonicalPath);
    }
}