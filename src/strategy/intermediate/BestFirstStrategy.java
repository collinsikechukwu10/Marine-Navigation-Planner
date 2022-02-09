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
     * Calculates the cost of entering a new state.
     *
     * @param previousNode previous node containing previous state
     * @param newState     new state
     * @param goal         goal of the search problem
     * @return cost of moving to the new state
     */
    @Override
    public float fCost(Node previousNode, Coord newState, Coord goal) {
        return manhattanDistance(newState, goal);
    }
}
