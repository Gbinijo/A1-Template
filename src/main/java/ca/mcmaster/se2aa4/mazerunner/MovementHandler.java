package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class MovementHandler {
    private Maze maze;
    private static final Logger logger = LogManager.getLogger(MovementHandler.class);

    public MovementHandler(Maze maze) {
        this.maze = maze;
    }

    private String turnRight(String direction) {
        switch (direction) {
            case "NORTH": return "EAST";
            case "EAST": return "SOUTH";
            case "SOUTH": return "WEST";
            case "WEST": return "NORTH";
            default: throw new IllegalStateException("Invalid direction");
        }
    }

    private String turnLeft(String direction) {
        switch (direction) {
            case "NORTH": return "WEST";
            case "EAST": return "NORTH";
            case "SOUTH": return "EAST";
            case "WEST": return "SOUTH";
            default: throw new IllegalStateException("Invalid direction");
        }
    }

    public List<Character> rightHandRuleAlgorithm(int[] start, int[] end, boolean[][] traversed) {
        List<Character> path = new ArrayList<>();
        int row = start[0];
        int column = start[1];
        String direction = "EAST";

        while (true) {
            if (row == end[0] && column == end[1]) {
                break;
            }

            String rightDirection = turnRight(direction);
            int[] rightPosition = attemptMove(row, column, rightDirection, traversed);

            if (rightPosition != null) {
                direction = rightDirection;
                row = rightPosition[0];
                column = rightPosition[1];
                path.add('R');
                path.add('F');
            } else {
                int[] forwardPosition = attemptMove(row, column, direction, traversed);
                if (forwardPosition != null) {
                    row = forwardPosition[0];
                    column = forwardPosition[1];
                    path.add('F');
                } else {
                    direction = turnLeft(direction);
                    path.add('L');
                }
            }
        }
        return path;
    }
    private int[] attemptMove(int row, int column, String direction, boolean[][] traversed) {
        int newRow = row;
        int newColumn = column;

        switch (direction) {
            case "NORTH": newRow -= 1; break;
            case "EAST": newColumn += 1; break;
            case "SOUTH": newRow += 1; break;
            case "WEST": newColumn -= 1; break;
        }

        if (newRow >= 0 && newRow < maze.getRows() && newColumn >= 0 && newColumn < maze.getColumns() && maze.getMazeArray()[newRow][newColumn] == ' ' && !traversed[newRow][newColumn]) {
            return new int[]{newRow, newColumn};
        }
        return null;
    }

    public boolean verifyPath(int[] start, int[] end, String path) {
        return true;
    }

    public String toFactorizedPath(List<Character> canonicalPath) {
        StringBuilder factorizedPath = new StringBuilder();
        int counter = 1;
        for (int i = 1; i < canonicalPath.size(); i++) {
            if (canonicalPath.get(i) == canonicalPath.get(i - 1)) {
                counter++;
            } else {
                if (counter > 1) {
                    factorizedPath.append(counter);
                }
                factorizedPath.append(canonicalPath.get(i - 1));
                counter = 1;
            }
        }
        if (counter > 1) {
            factorizedPath.append(counter);
        }
        factorizedPath.append(canonicalPath.get(canonicalPath.size() - 1));
        return factorizedPath.toString();
    }
}
