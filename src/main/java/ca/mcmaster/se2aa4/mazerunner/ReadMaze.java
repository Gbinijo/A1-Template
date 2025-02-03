package ca.mcmaster.se2aa4.mazerunner;

import java.io.IOException;

public class ReadMaze implements InputReader {
    @Override
    public Maze readMaze(String filePath) throws IOException {
        // Placeholder logic for database reader
        return new Maze(new char[0][0], 0, 0);
    }
}