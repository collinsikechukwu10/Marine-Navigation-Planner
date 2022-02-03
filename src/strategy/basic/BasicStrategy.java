package strategy.basic;

import core.Node;
import core.Coord;
import strategy.SearchStrategy;

import java.util.*;
import java.util.stream.Collectors;

public abstract class BasicStrategy extends SearchStrategy {

    @Override
    public void logFrontier(Deque<Node> frontier) {
        String frontierString = frontier.stream().map(path -> path.getState().toString()).collect(Collectors.joining(","));
        System.out.println("[" + frontierString + "]");
    }

    @Override
    public float cost(Node previousNode, Coord newState, Coord goal) {
        return previousNode.getTotalStepCost() + 1;
    }
}
