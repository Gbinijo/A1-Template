package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;
import java.util.ArrayList;

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
            throw new IllegalStateException("No start nor end position");
        }

        // Initialize position and direction
        int row = start[0];
        int column = start[1];
        String direction = "EAST";  // You can choose any starting direction
        traversed[row][column] = true;

        logger.info("Starting pathfinding at position: (" + row + ", " + column + "), direction: " + direction);

        while (true) {
            logger.info("At position: (" + row + ", " + column + "), direction: " + direction);

            // Check if we have reached the end
            if (row == end[0] && column == end[1]) {
                logger.info("Reached the end!");
                break;
            }

            // Try to move forward
            int[] newPosition = attemptMove(row, column, direction);
            if (newPosition != null) {
                row = newPosition[0];
                column = newPosition[1];
                path.add('F');  // Move forward
                logger.info("Moved forward to: (" + row + ", " + column + ")");
                continue;
            }

            // If moving forward fails, try turning right and then moving forward
            String nextDirection = turnRight(direction);
            newPosition = attemptMove(row, column, nextDirection);
            if (newPosition != null) {
                row = newPosition[0];
                column = newPosition[1];
                direction = nextDirection;
                path.add('R');  // Turn right
                path.add('F');  // Move forward
                logger.info("Turned right to: (" + row + ", " + column + "), direction: " + direction);
                continue;
            }

            // If moving right fails, try turning left and then move forward
            nextDirection = turnLeft(direction);
            newPosition = attemptMove(row, column, nextDirection);
            if (newPosition != null) {
                row = newPosition[0];
                column = newPosition[1];
                direction = nextDirection;
                path.add('L');  // Turn left
                path.add('F');  // Move forward
                logger.info("Turned left to: (" + row + ", " + column + "), direction: " + direction);
                continue;
            }

            // If all moves fail, we are stuck (should not happen in a valid maze)
            logger.error("Stuck in the maze, no valid moves!");
            throw new IllegalStateException("Stuck in the maze, no valid moves!");
        }

        return path;
    }

    // Helper method to attempt a move and return the new position if successful
    private int[] attemptMove(int row, int column, String direction) {
        int newRow = row;
        int newColumn = column;

        if (direction.equals("NORTH")) {
            newRow -= 1;
        } 
        else if (direction.equals("EAST")) {
            newColumn += 1;
        } 
        else if (direction.equals("SOUTH")) {
            newRow += 1;
        } 
        else if (direction.equals("WEST")) {
            newColumn -= 1;
        }

        logger.info("Trying to move " + direction + " to (" + newRow + ", " + newColumn + ")");
        if (canMove(newRow, newColumn)) {
            traversed[newRow][newColumn] = true;
            return new int[]{newRow, newColumn};
        }
        return null; // Move not possible
    }
}



    // public String factorizePath(List<Character> canonicalPath) {
    //     String factorizedPath = "";
    //     int count = 1;

    //     for (int i = 1; i < canonicalPath.size(); i++) {
    //         if (canonicalPath.get(i) == canonicalPath.get(i - 1)) {
    //             count++;
    //         } else {
    //             factorizedPath += (count > 1 ? count : "") + canonicalPath.get(i - 1);
    //             count = 1;
    //         }
    //     }

    //     // Add the last group
    //     factorizedPath += (count > 1 ? count : "") + canonicalPath.get(canonicalPath.size() - 1);
    //     return factorizedPath;
    // }
