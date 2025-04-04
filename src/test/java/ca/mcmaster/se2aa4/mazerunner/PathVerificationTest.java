package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PathVerificationTest {

    private Maze maze;
    private MazeNavigator navigator;

    @BeforeEach
    public void setup() throws IOException {
        // Load the known-working maze
        MazeFileReader reader = new MazeFileReader();
        maze = reader.readMaze("examples/straight.maz.txt");
        navigator = new MazeNavigator(maze);
    }

    @Test
    public void testCorrectPathReturnsTrue() {
        String correctPath = "4F";
        assertTrue(navigator.verifyPath(correctPath), "Expected path to be valid");
    }

    @Test
    public void testIncorrectPathReturnsFalse() {
        String incorrectPath = "3F";  // known to be invalid
        assertFalse(navigator.verifyPath(incorrectPath), "Expected path to be invalid");
    }

    @Test
    public void testRandomGarbagePathReturnsFalse() {
        String garbage = "XXFLYZZ3!";
        assertFalse(navigator.verifyPath(garbage), "Invalid characters should return false");
    }
}
