package strategy;

import core.Coord;
import core.Node;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Search Strategy Abstract class.
 * This is the base class for all search strategies implemented.
 * All search strategies must implement `logFrontier`, `orderFrontier` and `cost`.
 *
 * @author 210032207
 * @version 1.0.0
 * @since 30-01-2022
 */
public abstract class SearchStrategy {

    /**
     * Prints frontier information.
     *
     * @param frontier search frontier/agenda
     */
    public abstract void logFrontier(Deque<Node> frontier);

    /**
     * Appends and orders nodes in a frontier.
     *
     * @param frontier search frontier/agenda
     * @param nodes    new nodes
     * @param goal     goal of the search problem
     */
    public abstract void orderFrontier(Deque<Node> frontier, List<Node> nodes, Coord goal);

    /**
     * Generates the new nodes when from a list of new states, and adds the valid ones to the frontier.
     *
     * @param frontier    search frontier/agenda
     * @param explored    explored nodes
     * @param currentNode existing node to expand
     * @param newStates   new states
     * @param goal        goal of the search problem
     */
    public void expandNode(Deque<Node> frontier, List<Node> explored, Node currentNode, ArrayList<Coord> newStates, Coord goal) {
        List<Node> validNodes = new ArrayList<>();
        for (Coord newState : newStates) {
            Node node = new Node(newState, currentNode, fCost(currentNode, newState, goal));
            boolean stateExplored = stateExistsInNodes(explored, newState);
            boolean stateInFrontier = stateExistsInNodes(new ArrayList<>(frontier), newState);
            if (!stateExplored && !stateInFrontier) {
                validNodes.add(node);
            }
        }
        orderFrontier(frontier, validNodes, goal);
    }

    /**
     * Checks if a state is the current state in a list of nodes.
     *
     * @param nodes nodes to search
     * @param state state to find
     * @return True if state exists in the nodes
     */
    public boolean stateExistsInNodes(List<Node> nodes, Coord state) {
        return nodes.stream().map(Node::getState).anyMatch(s -> s.equals(state));
    }

    /**
     * Checks if a node has reached the goal of the search problem.
     *
     * @param node current node
     * @param goal goal of the search problem
     * @return True if node is at goal else False
     */
    public boolean isPathToGoal(Node node, Coord goal) {
        return node != null && node.getState().equals(goal);
    }

    /**
     * Calculates the cost of entering a new state. (May include heuristics)
     *
     * @param previousNode previous node containing previous state
     * @param newState     new state
     * @param goal         goal of the search problem
     * @return cost of moving to the new state
     */
    public abstract float fCost(Node previousNode, Coord newState, Coord goal);

    /**
     * Gets the class name of the strategy
     *
     * @return name of the strategy
     */
    public String name() {
        return this.getClass().getSimpleName();
    }


}
