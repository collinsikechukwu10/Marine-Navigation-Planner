package strategy.intermediate;

import core.Coord;
import core.Node;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Best First Search Strategy Abstract class.
 *
 * @author 210032207
 * @version 1.0.0
 * @since 30-01-2022
 */
public class BestFirstStrategy extends IntermediateStrategy {

    /**
     * Appends nodes and orders nodes in a frontier.
     * The heuristic is applied to sort the frontier.
     *
     * @param frontier search frontier/agenda
     * @param nodes    new nodes
     * @param goal     goal of the search problem
     */
    @Override
    public void orderFrontier(Deque<Node> frontier, List<Node> nodes, Coord goal) {
        // add paths to agenda
        frontier.addAll(nodes);
        // sort all paths in frontier by heuristic cost, so the lowest cost gets popped first
        frontier = frontier.stream().sorted(
                (p1, p2) -> (int) Math.ceil(manhattanDistance(p1.getState(), goal) - manhattanDistance(p2.getState(), goal))
        ).collect(Collectors.toCollection(ArrayDeque::new));
    }

    /**
     * Calculates the cost of entering a new state.
     *
     * @param previousNode previous node containing previous state
     * @param newState     new state
     * @param goal         goal of the search problem
     * @return cost of moving to the new state
     */
    @Override
    public float cost(Node previousNode, Coord newState, Coord goal) {
        return manhattanDistance(newState, goal);
    }
}
