package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.commons.cli.*;

import java.util.List;
import java.util.ArrayList;

import java.io.IOException;

public class MazeFileReader {
    public static Maze readMaze(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        int rows = 0;
        int columns = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                if (line.length() > columns) {
                    columns = line.length();
                }
                rows++;
            }
        }

        char[][] mazeArray = new char[rows][columns];
        for (int i = 0; i < rows; i++) {
            String mazeLine = lines.get(i);
            for (int j = 0; j < mazeLine.length(); j++) {
                mazeArray[i][j] = mazeLine.charAt(j);
            }
        }

        return new Maze(mazeArray, rows, columns);
    }
}