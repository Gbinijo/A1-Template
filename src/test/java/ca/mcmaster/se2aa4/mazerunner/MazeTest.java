package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MazeTest {

    @Test
    public void testGetRowsReturnsCorrectValue() {
        char[][] grid = {
            {'#', ' '},
            {' ', '#'},
            {'#', '#'}
        };
        Maze maze = new Maze(grid, 3, 2);
        assertEquals(3, maze.getRows(), "Maze should report 3 rows");
    }

    @Test
    public void testGetColumnsReturnsCorrectValue() {
        char[][] grid = {
            {'#', ' '},
            {' ', '#'}
        };
        Maze maze = new Maze(grid, 2, 2);
        assertEquals(2, maze.getColumns(), "Maze should report 2 columns");
    }

    @Test
    public void testMazeArrayIsStoredCorrectly() {
        char[][] grid = {
            {'#', ' '},
            {' ', '#'}
        };
        Maze maze = new Maze(grid, 2, 2);
        char[][] returnedGrid = maze.getMazeArray();

        assertEquals('#', returnedGrid[0][0], "Top-left corner should be '#'");
        assertEquals(' ', returnedGrid[0][1], "Top-right should be space");
        assertEquals('#', returnedGrid[1][1], "Bottom-right should be '#'");
    }
}
