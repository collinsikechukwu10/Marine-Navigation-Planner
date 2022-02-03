package strategy.intermediate;

import core.Coord;
import core.Node;
import core.TriangleGridCoord;
import strategy.SearchStrategy;

import java.util.Deque;
import java.util.stream.Collectors;

public abstract class IntermediateStrategy extends SearchStrategy {



    @Override
    public void logFrontier(Deque<Node> frontier) {
        String frontierString = frontier.stream().map(path -> path.getState().toString() + ":" + path.getCost()).collect(Collectors.joining(","));
        System.out.println("[" + frontierString + "]");
    }

    public float manhattanDistance(Coord currentPoint, Coord goal) {
        TriangleGridCoord p1 = TriangleGridCoord.convertFrom(currentPoint);
        TriangleGridCoord p2 = TriangleGridCoord.convertFrom(goal);
        return Math.abs(p1.getA() - p2.getA()) + Math.abs(p1.getB() - p2.getB()) + Math.abs(p1.getC() - p2.getC());
    }

}
