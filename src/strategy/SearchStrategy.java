package strategy;

import core.Coord;
import core.Node;

import java.util.*;

/**
 * Search Strategy class.
 * This is the base class for all search strategies implemented.
 *
 * @author 210032207
 * @version 1.0.0
 * @since 30-01-2022
 */
public abstract class SearchStrategy {

    public abstract void logFrontier(Deque<Node> frontier);

    public abstract void orderFrontier(Deque<Node> agenda, List<Node> nodes, Coord goal);

    public void expandPath(Deque<Node> frontier, List<Node> extendedList, Node currentNode, ArrayList<Coord> newStates, Coord goal) {
        List<Node> validNodes = new ArrayList<>();
        for (Coord newState : newStates) {
            Node node = new Node(newState, currentNode, cost(currentNode, newState, goal));
            boolean stateExplored = extendedList.stream().map(Node::getState).anyMatch(s -> s.equals(newState));
            boolean stateInFrontier = frontier.stream().map(Node::getState).anyMatch(s -> s.equals(newState));
            if (!stateExplored && !stateInFrontier) {
                validNodes.add(node);
            }
        }
        orderFrontier(frontier, validNodes, goal);
    }

    public boolean isPathToGoal(Node node, Coord goal) {
        return node != null && node.getState().equals(goal);
    }

    public abstract float cost(Node previousNode, Coord newState, Coord goal);

    public String name() {
        return this.getClass().getSimpleName();
    }


}
