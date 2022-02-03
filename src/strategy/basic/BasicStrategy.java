package strategy.basic;

import core.Coord;
import core.Node;
import strategy.SearchStrategy;

import java.util.Deque;
import java.util.stream.Collectors;

/**
 * Basic Search Strategy Abstract class.
 * This is the base class for all basic search strategies implemented.
 * All basic search strategies must implement `orderFrontier`.
 *
 * @author 210032207
 * @version 1.0.0
 * @since 30-01-2022
 */
public abstract class BasicStrategy extends SearchStrategy {

    /**
     * Prints frontier information for basic strategies.
     *
     * @param frontier search frontier/agenda
     */
    @Override
    public void logFrontier(Deque<Node> frontier) {
        String frontierString = frontier.stream().map(path -> path.getState().toString()).collect(Collectors.joining(","));
        System.out.println("[" + frontierString + "]");
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
    public float cost(Node previousNode, Coord newState, Coord goal) {
        return previousNode.getTotalStepCost() + 1;
    }
}
