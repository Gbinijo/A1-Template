package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Direction implements MazeSolver {
    private final MazeNavigator navigator;
    private static final Logger logger = LogManager.getLogger(Direction.class);

    public Direction(Maze maze) {
        this.navigator = new MazeNavigator(maze);
    }

    @Override
    public List<Character> rightHandRuleAlgorithm() {
        return navigator.rightHandRuleAlgorithm();
    }

    @Override
    public boolean verifyPath(String path) {
        return navigator.verifyPath(path);
    }

    @Override
    public String toFactorizedPath(List<Character> canonicalPath) {
        return navigator.toFactorizedPath(canonicalPath);
    }

    public MazeNavigator getNavigator() {
        return this.navigator;
    }
}
