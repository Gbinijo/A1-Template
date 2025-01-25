package ca.mcmaster.se2aa4.mazerunner;

public class Maze{
    private char[][] mazeArray;
    private int rows;
    private int columns;

    public Maze(char[][] mazeArray, int rows, int columns){
        this.mazeArray = mazeArray;
        this.rows = rows;
        this.columns = columns;
    }

    public int getRows(){
        return rows;
    }
    public int getColumns(){
        return columns;
    }
    public char[][] getMazeArray(){
        return mazeArray;
    }

}