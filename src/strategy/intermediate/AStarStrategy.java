package strategy.intermediate;

import core.Coord;
import core.Node;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class AStarStrategy extends IntermediateStrategy {
    @Override
    public void orderFrontier(Deque<Node> agenda, List<Node> nodes, Coord goal) {
        // add paths to agenda
        agenda.addAll(nodes);
        // sort all paths in frontier by (path_cost + heuristic_cost), so the lowest cost gets popped first
        agenda = agenda.stream().sorted(
                (p1, p2) -> (int) Math.ceil(totalCost(p1, goal) - totalCost(p2, goal))
        ).collect(Collectors.toCollection(ArrayDeque::new));
    }

    private float totalCost(Node currentNode, Coord goal) {
        return currentNode.getCost() + manhattanDistance(currentNode.getState(), goal);
    }

    @Override
    public float cost(Node previousNode, Coord newState, Coord goal) {
        return previousNode.getTotalStepCost() + 1 + manhattanDistance(newState, goal);
    }
}
