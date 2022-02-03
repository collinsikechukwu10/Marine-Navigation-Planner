package strategy.intermediate;

import core.Coord;
import core.Node;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AStar Search Strategy Abstract class.
 *
 * @author 210032207
 * @version 1.0.0
 * @since 30-01-2022
 */
public class AStarStrategy extends IntermediateStrategy {
    /**
     * Appends nodes and orders nodes in a frontier.
     * The nodes are ordered based on the f_cost (path_cost + heuristic_cost)
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
        frontier = frontier.stream().sorted(
                (p1, p2) -> (int) Math.ceil(totalCost(p1, goal) - totalCost(p2, goal))
        ).collect(Collectors.toCollection(ArrayDeque::new));
    }

    /**
     * This is the total cost (for the A* strategy, this is the f_cost .i.e. path_cost + heuristic_cost)
     *
     * @param currentNode current node
     * @param goal        goal of the search problem
     * @return f_cost of the node provided
     */
    private float totalCost(Node currentNode, Coord goal) {
        return currentNode.getCost() + manhattanDistance(currentNode.getState(), goal);
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
        return previousNode.getTotalStepCost() + 1 + manhattanDistance(newState, goal);
    }
}
