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
        int row = start[0];
        int column = start[1];
        String direction = "EAST"; // Assuming EAST as the initial direction

        // Convert factorized path (e.g., "4F" -> "FFFF", "3R2F" -> "RRRFF")
        String expandedPath = expandFactorizedPath(path);

        for (char move : expandedPath.toCharArray()) {
            switch (move) {
                case 'F': // Move forward
                    int[] newPos = attemptMove(row, column, direction, new boolean[maze.getRows()][maze.getColumns()]);
                    if (newPos == null) {
                        return false; // Invalid move
                    }
                    row = newPos[0];
                    column = newPos[1];
                    break;
                case 'R': // Turn right
                    direction = turnRight(direction);
                    break;
                case 'L': // Turn left
                    direction = turnLeft(direction);
                    break;
                default:
                    return false; // Invalid character in path
            }
        }

        // Ensure the final position matches the expected end position
        return row == end[0] && column == end[1];
    }

    private String expandFactorizedPath(String path) {
        StringBuilder expanded = new StringBuilder();
        StringBuilder numBuffer = new StringBuilder();

        for (char c : path.toCharArray()) {
            if (Character.isDigit(c)) {
                numBuffer.append(c); // Accumulate digits
            } else {
                int repeat = numBuffer.length() > 0 ? Integer.parseInt(numBuffer.toString()) : 1;
                for (int i = 0; i < repeat; i++) {
                    expanded.append(c);
                }
                numBuffer.setLength(0); // Reset buffer
            }
        }

        return expanded.toString();
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
