package ca.mcmaster.se2aa4.mazerunner;

import java.io.IOException;

interface InputReader{
    Maze readMaze(String filePath) throws IOException;
}