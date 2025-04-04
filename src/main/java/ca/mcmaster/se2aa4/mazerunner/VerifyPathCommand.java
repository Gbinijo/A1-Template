package ca.mcmaster.se2aa4.mazerunner;

public class VerifyPathCommand implements Command {
    private final Direction solver;
    private final String path;

    public VerifyPathCommand(Direction solver, String path) {
        this.solver = solver;
        this.path = path;
    }

    @Override
    public void execute() {
        boolean isValid = solver.verifyPath(path);
        if (isValid) {
            System.out.println("Path verification result: Valid");
        } else {
            System.out.println("Path verification result: Invalid");
        }
    }
}
