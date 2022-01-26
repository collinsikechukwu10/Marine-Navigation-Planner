package strategy.basic;

import core.Coord;
import core.Path;
import strategy.SearchStrategy;

import java.util.*;
import java.util.stream.Collectors;

public abstract class BasicStrategy extends SearchStrategy {

    public float stepCost() {
        return 1;
    }

    @Override
    public boolean isPathToGoal(Path path, Coord goal) {
        return path != null && path.getState().equals(goal);
    }

    @Override
    public float heuristicCost(Coord action, Coord goal) {
        return 0;
    }

    @Override
    public void logFrontier(Deque<Path> frontier) {
        String frontierString = frontier.stream().map(path -> path.getState().toString()).collect(Collectors.joining(","));
        System.out.println("[" + frontierString + "]");
    }

}
