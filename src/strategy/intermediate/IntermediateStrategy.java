package strategy.intermediate;

import core.Coord;
import core.Node;
import core.TriangleGridCoord;
import strategy.SearchStrategy;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Intermediate Search Strategy Abstract class.
 * This is the base class for all intermediate search strategies implemented.
 * All intermediate search strategies must implement `orderFrontier` and `cost`.
 *
 * @author 210032207
 * @version 1.0.0
 * @since 30-01-2022
 */
public abstract class IntermediateStrategy extends SearchStrategy {


    /**
     * Prints frontier information for intermediate strategies.
     *
     * @param frontier search frontier/agenda
     */
    @Override
    public void logFrontier(Deque<Node> frontier) {
        String frontierString = frontier.stream().map(path -> path.getState().toString() + ":" + path.getCost()).collect(Collectors.joining(","));
        System.out.println("[" + frontierString + "]");
    }

    /**
     * Appends nodes and orders nodes in a frontier.
     *
     * @param frontier search frontier/agenda
     * @param nodes    new nodes
     * @param goal     goal of the search problem
     */
    @Override
    public void orderFrontier(Deque<Node> frontier, List<Node> nodes, Coord goal) {
        // add paths to agenda
        frontier.addAll(nodes);
        // sort all paths in frontier by (path_cost + heuristic_cost), so the lowest cost gets popped first
        frontier = frontier.stream().sorted().collect(Collectors.toCollection(ArrayDeque::new));
    }

    /**
     * Calculates the manhattan distance between 2 points.
     * This distance metric is based on a triangle grid system.
     *
     * @param point1 first point
     * @param point2 second point
     * @return manhattan distance between provided points
     */
    public float manhattanDistance(Coord point1, Coord point2) {
        TriangleGridCoord p1 = TriangleGridCoord.convertFromCoord(point1);
        TriangleGridCoord p2 = TriangleGridCoord.convertFromCoord(point2);
        return Math.abs(p1.getA() - p2.getA()) + Math.abs(p1.getB() - p2.getB()) + Math.abs(p1.getC() - p2.getC());
    }

}
