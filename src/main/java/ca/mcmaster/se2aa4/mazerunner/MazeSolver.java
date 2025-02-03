package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

interface MazeSolver {
    List<Character> rightHandRuleAlgorithm();
    boolean verifyPath(String path);
    String toFactorizedPath(List<Character> path);
}