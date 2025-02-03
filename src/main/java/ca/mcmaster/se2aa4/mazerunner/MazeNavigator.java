package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class MazeNavigator {
    private Maze maze;
    private int[] start;
    private int[] end;
    private boolean[][] traversed;
    private List<Character> path;
    private MovementHandler movementHandler;
    private static final Logger logger = LogManager.getLogger(MazeNavigator.class);

    public MazeNavigator(Maze maze) {
        this.maze = maze;
        this.traversed = new boolean[maze.getRows()][maze.getColumns()];
        this.path = new ArrayList<>();
        this.movementHandler = new MovementHandler(maze);
        getStartEnd();
    }

    private void getStartEnd() {
        int rows = maze.getRows();
        int columns = maze.getColumns();
        char[][] twoDimMaze = maze.getMazeArray();

        logger.info("Maze Structure:");
        for (int i = 0; i < rows; i++) {
            logger.info(new String(twoDimMaze[i]));
        }

        start = new int[]{-1, -1};
        end = new int[]{-1, -1};

        for (int i = 0; i < rows; i++) {
            if (twoDimMaze[i][0] == ' ') {
                start = new int[]{i, 0};
                break;
            }
        }

        for (int i = 0; i < rows; i++) {
            if (twoDimMaze[i][columns - 1] == ' ') {
                end = new int[]{i, columns - 1};
                break;
            }
        }

        if (start[0] == -1 || end[0] == -1) {
            throw new IllegalStateException("Invalid start or end position");
        }

        logger.info("Start position: (" + start[0] + ", " + start[1] + ")");
        logger.info("End position: (" + end[0] + ", " + end[1] + ")");
    }

    public List<Character> rightHandRuleAlgorithm() {
        return movementHandler.rightHandRuleAlgorithm(start, end, traversed);
    }

    public boolean verifyPath(String path) {
        return movementHandler.verifyPath(start, end, path);
    }

    public String toFactorizedPath(List<Character> canonicalPath) {
        return movementHandler.toFactorizedPath(canonicalPath);
    }
}