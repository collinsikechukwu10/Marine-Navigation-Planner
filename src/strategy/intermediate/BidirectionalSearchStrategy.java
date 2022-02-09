package strategy.intermediate;

import core.Coord;
import core.Node;

import java.util.Deque;
import java.util.List;

/**
 * Bidirectional Search Strategy class.
 * This is exactly the same as the BreadthFirstSearchStrategy.
 * However, unlike breadth first, it must use more than one agent for the search
 *
 * @author 210032207
 * @version 1.0.0
 * @since 09-02-2022
 */
public class BidirectionalSearchStrategy extends IntermediateStrategy {

    /**
     * Appends nodes and orders nodes in a frontier.
     * `  *
     *
     * @param frontier search frontier/agenda
     * @param nodes    new nodes
     * @param goal     goal of the search problem
     */
    @Override
    public void orderFrontier(Deque<Node> frontier, List<Node> nodes, Coord goal) {
        // ordering of paths is currently at decreasing levels of action priority as defined by the tie-breaking strategy
        for (Node node : nodes) {
            frontier.addLast(node);
        }
    }

    /**
     * Calculates the cost of entering a new state. This just adds a step cost of 1.
     *
     * @param previousNode previous node containing previous state
     * @param newState     new state
     * @param goal         goal of the search problem
     * @return cost of moving to the new state
     */
    @Override
    public float fCost(Node previousNode, Coord newState, Coord goal) {
        return 1;
    }
}
