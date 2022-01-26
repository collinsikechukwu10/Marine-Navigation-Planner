package strategy.intermediate;

import core.Coord;
import core.Map;
import core.Path;
import strategy.SearchStrategy;

import java.util.Deque;
import java.util.stream.Collectors;

public abstract class IntermediateStrategy extends SearchStrategy {

    @Override
    public float stepCost() {
        return 1;
    }

    @Override
    public float heuristicCost(Coord action, Coord goal) {
        return manhattanDistance(action, goal);
    }

    @Override
    public void logFrontier(Deque<Path> frontier) {
        String frontierString = frontier.stream().map(path -> path.getState().toString() + ":" + path.getCost()).collect(Collectors.joining(","));
        System.out.println("[" + frontierString + "]");
    }

    public float manhattanDistance(Coord currentPoint, Coord goal) {
        int[] cpTriangle = toTriangleCoordinate(currentPoint);
        int[] goalTriangle = toTriangleCoordinate(goal);
        return manhattanDistance(cpTriangle[0], cpTriangle[1], cpTriangle[2],
                goalTriangle[0], goalTriangle[1], goalTriangle[2]);
//        return Math.abs(currentPoint.getR() - goal.getR()) + Math.abs(currentPoint.getC() - goal.getC());
    }

    private float manhattanDistance(int a1, int b1, int c1, int a2, int b2, int c2) {
        // https://www.boristhebrave.com/2021/05/23/triangle-grids/
        return Math.abs(a1 - a2) + Math.abs(b1 - b2) + Math.abs(c1 - c2);
    }


    private int[] toTriangleCoordinate(Coord coord) {

    }
}
