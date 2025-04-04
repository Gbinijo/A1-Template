package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MazeCLITest {

    @Test
    public void testInputFileFlag() {
        String[] args = {"-i", "maze.txt"};
        MazeCLI cli = new MazeCLI(args);
        assertEquals("maze.txt", cli.getInputFile());
    }

    @Test
    public void testPathVerificationFlag() {
        String[] args = {"-p", "3F2R"};
        MazeCLI cli = new MazeCLI(args);
        assertEquals("3F2R", cli.getPathToVerify());
    }

    @Test
    public void testBothFlagsTogether() {
        String[] args = {"-i", "maze.txt", "-p", "3F2R"};
        MazeCLI cli = new MazeCLI(args);
        assertEquals("maze.txt", cli.getInputFile());
        assertEquals("3F2R", cli.getPathToVerify());
    }
}
