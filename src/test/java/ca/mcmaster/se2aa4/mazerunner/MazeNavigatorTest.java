package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MazeNavigatorTest {

    private Maze maze;

    @BeforeEach
    public void setup() {
        char[][] grid = {
            {'#', '#', '#', '#'},
            {' ', ' ', ' ', '#'},
            {'#', '#', ' ', ' '}
        };
        // Start is at (1, 0), End is at (2, 3)
        maze = new Maze(grid, 3, 4);
    }

    @Test
    public void testStartAndEndDetectedCorrectly() {
        MazeNavigator navigator = new MazeNavigator(maze);
        // The constructor logs start/end positions — if they're invalid, it throws
        assertNotNull(navigator); // If construction succeeded, test passes
    }

    @Test
    public void testMazeWithNoStartThrows() {
        char[][] noStart = {
            {'#', '#'},
            {'#', '#'}
        };
        Maze maze = new Maze(noStart, 2, 2);
        assertThrows(IllegalStateException.class, () -> new MazeNavigator(maze));
    }

    @Test
    public void testMazeWithNoEndThrows() {
        char[][] noEnd = {
            {' ', '#'},
            {'#', '#'}
        };
        Maze maze = new Maze(noEnd, 2, 2);
        assertThrows(IllegalStateException.class, () -> new MazeNavigator(maze));
    }
}