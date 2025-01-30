package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Direction {
    private Maze maze;
    private int[] start;
    private int[] end;
    private boolean[][] traversed;
    private List<Character> path;

    // Create a logger to track progress
    private static final Logger logger = LogManager.getLogger(Direction.class);

    public Direction(Maze maze) {
        this.maze = maze;
        this.start = new int[2];
        this.end = new int[2];
        this.traversed = new boolean[maze.getRows()][maze.getColumns()];
        this.path = new ArrayList<>();
        getStartEnd();
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

    private boolean canMove(int row, int column) {
        return row >= 0 && row < maze.getRows() &&
            column >= 0 && column < maze.getColumns() &&
            maze.getMazeArray()[row][column] == ' ' && !traversed[row][column];
    }

    private void getStartEnd() {
        int rows = maze.getRows();
        int columns = maze.getColumns();
        char[][] twoDimMaze = maze.getMazeArray();

        logger.info("Maze Structure:");
        for (int i = 0; i < rows; i++) {
            logger.info(new String(twoDimMaze[i]));
        }

        // Initialize start and end to be invalid positions
        start[0] = -1;
        start[1] = -1;
        end[0] = -1;
        end[1] = -1;

        // Look for the start position (find the first empty space on the left edge)
        for (int i = 0; i < rows; i++) {
            if (twoDimMaze[i][0] == ' ') {  // ' ' is open space
                start[0] = i;
                start[1] = 0;
                break;
            }
        }

        // Look for the end position (find the first empty space on the right edge)
        for (int i = 0; i < rows; i++) {
            if (twoDimMaze[i][columns - 1] == ' ') {  // ' ' is open space
                end[0] = i;
                end[1] = columns - 1;
                break;
            }
        }

        // Ensure the start and end positions are valid and not the same point
        if (start[0] == -1 || start[1] == -1) {
            throw new IllegalStateException("No valid entry point found in the maze.");
        }
        if (end[0] == -1 || end[1] == -1 || (start[0] == end[0] && start[1] == end[1])) {
            throw new IllegalStateException("No valid exit point found or start and end are the same.");
        }

        logger.info("Start position: (" + start[0] + ", " + start[1] + ")");
        logger.info("End position: (" + end[0] + ", " + end[1] + ")");
    }

    public int[] startPosition() {
        return start;
    }

    public int[] endPosition() {
        return end;
    }

    public List<Character> rightHandRuleAlgorithm() {
        if (start == null || end == null) {
            throw new IllegalStateException("No start or end position.");
        }

        // Initialize position and direction
        int row = start[0];
        int column = start[1];
        String direction = "EAST"; // Starting direction

        logger.info("Starting pathfinding at position: (" + row + ", " + column + "), direction: " + direction);

        while (true) {
            logger.info("At position: (" + row + ", " + column + "), direction: " + direction);

            // Check if we have reached the end
            if (row == end[0] && column == end[1]) {
                logger.info("Reached the end!");
                break;
            }

            // Check the position to the right of the current direction
            String rightDirection = turnRight(direction);
            int[] rightPosition = attemptMove(row, column, rightDirection);

            if (rightPosition != null) {
                // If the space to the right is free, turn right and move forward
                direction = rightDirection;
                row = rightPosition[0];
                column = rightPosition[1];
                path.add('R'); // Turn right
                path.add('F'); // Move forward
                logger.info("Turned right and moved to: (" + row + ", " + column + "), direction: " + direction);
            } else {
                // If the space to the right is blocked, try to move forward
                int[] forwardPosition = attemptMove(row, column, direction);

                if (forwardPosition != null) {
                    row = forwardPosition[0];
                    column = forwardPosition[1];
                    path.add('F'); // Move forward
                    logger.info("Moved forward to: (" + row + ", " + column + ")");
                } else {
                    // If forward is also blocked, turn left
                    direction = turnLeft(direction);
                    path.add('L'); // Turn left
                    logger.info("Turned left to face: " + direction);
                }
            }
        }

        return path;
    }

// Helper method to attempt a move and return the new position if successful
    private int[] attemptMove(int row, int column, String direction) {
        int newRow = row;
        int newColumn = column;

        if (direction.equals("NORTH")) {
            newRow -= 1;
        } else if (direction.equals("EAST")) {
            newColumn += 1;
        } else if (direction.equals("SOUTH")) {
            newRow += 1;
        } else if (direction.equals("WEST")) {
            newColumn -= 1;
        }

        logger.info("Trying to move " + direction + " to (" + newRow + ", " + newColumn + ")");
        if (canMove(newRow, newColumn)) {
            return new int[]{newRow, newColumn};
        }
        return null; // Move not possible
    }

    public String toFactorizedPath(List<Character> canonicalPath) {
        String factorizedPath = "";
        int counter = 1;

        for (int i = 1; i < canonicalPath.size(); i++) {
            if (canonicalPath.get(i) == canonicalPath.get(i - 1)) {
                counter++;
            } else {
                if (counter > 1) {
                    factorizedPath += counter;
                }
                factorizedPath += canonicalPath.get(i - 1);
                counter = 1;
            }
        }

        // Append the last group
        if (counter > 1) {
            factorizedPath += counter;
        }
        factorizedPath += canonicalPath.get(canonicalPath.size() - 1);
        
        return factorizedPath;
    }

    public boolean verifyPath(String path) {
        int row = start[0];
        int column = start[1];
        String direction = "EAST";

        for (char move : path.toCharArray()) {
            if (move == 'F') {
                int[] newPosition = attemptMove(row, column, direction);
                if (newPosition == null) {
                    return false; // Invalid move
                }
                row = newPosition[0];
                column = newPosition[1];
            } else if (move == 'R') {
                direction = turnRight(direction);
            } else if (move == 'L') {
                direction = turnLeft(direction);
            } else {
                return false; // Invalid character in path
            }
        }
        return row == end[0] && column == end[1]; // True if it reaches the exit
    }
}