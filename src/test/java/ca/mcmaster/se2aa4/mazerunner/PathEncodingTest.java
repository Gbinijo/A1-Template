package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PathEncodingTest {

    @Test
    public void testToFactorizedPathSimpleSequence() {
        List<Character> canonical = Arrays.asList('F', 'F', 'F', 'R', 'R', 'F');
        Maze dummyMaze = new Maze(new char[1][1], 1, 1);
        MovementHandler handler = new MovementHandler(dummyMaze);

        String factorized = handler.toFactorizedPath(canonical);
        assertEquals("3F2RF", factorized);
    }

    @Test
    public void testToFactorizedPathNoRepetitions() {
        List<Character> canonical = Arrays.asList('F', 'R', 'L', 'F');
        Maze dummyMaze = new Maze(new char[1][1], 1, 1);
        MovementHandler handler = new MovementHandler(dummyMaze);

        String factorized = handler.toFactorizedPath(canonical);
        assertEquals("FRLF", factorized);
    }

    @Test
    public void testExpandFactorizedPathViaReflection() throws Exception {
        String factorized = "3F2RFL";
        Maze dummyMaze = new Maze(new char[1][1], 1, 1);
        MovementHandler handler = new MovementHandler(dummyMaze);

        Method expandMethod = MovementHandler.class.getDeclaredMethod("expandFactorizedPath", String.class);
        expandMethod.setAccessible(true); // allow access to private method

        String expanded = (String) expandMethod.invoke(handler, factorized);
        assertEquals("FFFRRFL", expanded);
    }
}
