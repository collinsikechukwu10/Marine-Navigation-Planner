package agent;

import core.Action;
import core.Coord;
import core.Node;
import strategy.SearchStrategy;
import strategy.intermediate.BidirectionalSearchStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Agent Class.
 * This class is used to traverse the map using any of the implemented strategies subclassed by `SearchStrategy`
 * <p>
 * It prints out the following during and after traversal:
 * + coordinates in the nodes of the frontier at each step before polling a node.
 * + the proposed path discovered.
 * + the proposed path cost.
 * + the number of steps taken by the search algorithm.
 *
 * @author 210032207
 * @version 1.0.0
 * @since 30-01-2022
 */
public class Agent extends AbstractAgent {
    private static final int LAND = 1;

    /**
     * Constructor for agent class.
     *
     * @param map   map to traverse
     * @param start departure or start point
     * @param goal  destination or end point
     */
    public Agent(int[][] map, Coord start, Coord goal, SearchStrategy strategy) {
        super(map, start, goal, strategy);
    }


    /**
     * Traverse the map provided from the departure/start point to the destination/end point.
     */
    public void traverse() {
        if (strategy instanceof BidirectionalSearchStrategy) {
            bidirectionalTraversal();
        } else {
            unidirectionalTraversal();
        }
    }

    /**
     * Performs search for uni directional search algorithms
     */
    public void unidirectionalTraversal() {
        frontier.add(new Node(start, null, 0));
        int count = 0;
        Node proposedNode = Node.FAILURE;

        while (!frontier.isEmpty()) {
            count++;
            Node optimalNode = step();
            if (optimalNode != null) {
                proposedNode = optimalNode;
                break;
            }
        }
        // store proposed node and the steps. to be used for evaluation purposes
        setProposedNode(proposedNode);
        setSteps(count);
        // log results
        logSearchResult(proposedNode, count);
    }

    /**
     * Performs search for bidirectional search
     */
    public void bidirectionalTraversal() {
        // create forward agent going from start to goal and create backward agent going from goal to start
        Agent forwardAgent = new Agent(map, start, goal, strategy);
        Agent backwardAgent = new Agent(map, goal, start, strategy);
        // run bidirectional steps
        int count = 0;
        Node proposedNode = Node.FAILURE;
        while (!forwardAgent.getFrontier().isEmpty() && !backwardAgent.getFrontier().isEmpty()) {
            count++;
            System.out.print("[Forward] ");
            Node forwardOptimalNode = forwardAgent.step();
            System.out.print("[Backward] ");
            Node backwardOptimalNode = backwardAgent.step();
            if (forwardOptimalNode != null) {
                proposedNode = forwardOptimalNode;
                break;
            } else if (backwardOptimalNode != null) {
                proposedNode = backwardOptimalNode;
                break;
            } else {
                // check if both agents have visited the same node
                Node optimalNode = extractIntersectingPath(forwardAgent.getExplored(), backwardAgent.getExplored());
                if (optimalNode != null) {
                    proposedNode = optimalNode;
                    break;
                }
            }
        }
        // store proposed node and the steps. to be used for evaluation purposes
        setProposedNode(proposedNode);
        setSteps(count);
        // log results
        logSearchResult(proposedNode, count);
    }

    /**
     * Runs a search step.
     *
     * @return proposed node if exists
     */
    public Node step() {
        strategy.logFrontier(frontier);
        Node currentNode = frontier.pollFirst();
        if (strategy.isPathToGoal(currentNode, goal)) {
            return currentNode;
        } else {
            explored.add(currentNode);
            ArrayList<Coord> validNewStates = checkAvailableActions(currentNode.getState());
            strategy.expandPath(frontier, explored, currentNode, validNewStates, goal);
        }
        return null;
    }

    /**
     * Check if state is out of bounds.
     *
     * @param coord coordinate in the map
     * @return True if state is v
     */
    public boolean isOutOfBounds(Coord coord) {
        int r = coord.getR();
        int c = coord.getC();
        int mapRows = map.length;
        int mapCols = map[0].length;
        //  check if state does not exist in the map( i.e. out of bounds), and if state is on land
        return r < 0 || r >= mapRows || c < 0 || c >= mapCols;
    }

    /**
     * Checks if a state is on land.
     *
     * @param coord coordinate in the map
     * @return True if state is on land
     */
    public boolean isLand(Coord coord) {
        return this.map[coord.getR()][coord.getC()] == LAND;
    }

    /**
     * Gets the valid states that an agent can move to from a current state.
     *
     * @param state current state
     * @return valid states
     */
    private ArrayList<Coord> checkAvailableActions(Coord state) {
        // Triangles pointing upwards only exists at points where r+c is even
        // Actions are arranged in order of priority
        ArrayList<Coord> validNewStates = new ArrayList<>();
        Action.getAllowedActions(state, useAdvancedFeatures).forEach(ac -> {
            Coord newState = ac.move(state);
            if (!isOutOfBounds(newState) && !isLand(newState)) {
                validNewStates.add(newState);
            }
        });
        return validNewStates;
    }

    /**
     * Finds a node that connects intersects two list of nodes.
     *
     * @param forwardNodeList  primary node list
     * @param backwardNodeList secondary node list
     * @return connecting node
     */
    public Node extractIntersectingPath(List<Node> forwardNodeList, List<Node> backwardNodeList) {
        List<Coord> forwardCoords = forwardNodeList.stream().map(Node::getState).collect(Collectors.toList());
        List<Coord> backwardCoords = backwardNodeList.stream().map(Node::getState).collect(Collectors.toList());
        forwardCoords.retainAll(backwardCoords);
        // only get the first coordinate if intersection exists.
        if (forwardCoords.size() > 0) {
            Coord intersection = new ArrayList<>(forwardCoords).get(0);
            System.out.println("Intersecting state at " + intersection);
            Optional<Node> primaryNodeOptional = forwardNodeList.stream().filter(nd -> nd.getState().equals(intersection)).findFirst();
            Optional<Node> backwardNodeOptional = backwardNodeList.stream().filter(nd -> nd.getState().equals(intersection)).findFirst();
            if (primaryNodeOptional.isPresent() && backwardNodeOptional.isPresent()) {
                // to prevent combining the same node twice, we read from the parent of the primary node
                return mergeNodes(primaryNodeOptional.get().getParent(), backwardNodeOptional.get());
            }
        }
        return null;
    }

    /**
     * Merge two nodes together.
     *
     * @param node1 primary node
     * @param node2 secondary node
     * @return merged node
     */
    private Node mergeNodes(Node node1, Node node2) {
        Node mergedNode = node1;
        Node b = node2;
        while (b != null) {
            Coord newState = b.getState();
            mergedNode = new Node(newState, mergedNode, strategy.cost(mergedNode, newState, goal));
            b = b.getParent();
        }
        return mergedNode;
    }

}
