package ca.mcmaster.se2aa4.mazerunner;

public class CmdFactory {

    public static Command createCommand(String pathToVerify, Maze maze) {
        Direction solver = new Direction(maze);

        if (pathToVerify != null) {
            return new VerifyPathCommand(solver, pathToVerify);
        } else {
            return new SolveMazeCommand(solver);
        }
    }
}
