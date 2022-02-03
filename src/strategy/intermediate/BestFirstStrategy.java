package strategy.intermediate;

import java.util.Deque;
import java.util.ArrayDeque;
import java.util.List;
import java.util.stream.Collectors;

import core.Coord;
import core.Node;


public class BestFirstStrategy extends IntermediateStrategy {


    @Override
    public void orderFrontier(Deque<Node> agenda, List<Node> nodes, Coord goal) {
        // add paths to agenda
        agenda.addAll(nodes);
        // sort all paths in frontier by heuristic cost, so the lowest cost gets popped first
        agenda = agenda.stream().sorted(
                (p1, p2) -> (int) Math.ceil(manhattanDistance(p1.getState(), goal) - manhattanDistance(p2.getState(), goal))
        ).collect(Collectors.toCollection(ArrayDeque::new));
    }
    @Override
    public float cost(Node previousNode, Coord newState, Coord goal) {
        return manhattanDistance(newState,goal);
    }
}
