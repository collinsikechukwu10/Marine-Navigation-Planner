package agent;

import core.Action;
import core.Coord;
import core.Node;
import strategy.SearchStrategy;

import java.util.ArrayList;

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
public class Agent extends BaseAgent {
    private static final int LAND = 1;

    private Node proposedNode;



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
     * Check if state is valid.
     *
     * @param state coordinate in the map
     * @return
     */
    public boolean isValidState(Coord state) {
        int r = state.getR();
        int c = state.getC();
        int mapRows = map.length;
        int mapCols = map[0].length;
        //  check if state does not exist in the map( i.e. out of bounds), and if state is on land
        boolean isOutOfBounds = r < 0 || r >= mapRows || c < 0 || c >= mapCols;
        return !isOutOfBounds && !(this.map[r][c] == LAND);
    }

    private ArrayList<Coord> checkAvailableActions(Coord state) {
        // Triangles pointing upwards only exists at points where r+c is even
        // Actions are arranged in order of priority
        ArrayList<Coord> validNewStates = new ArrayList<>();
        Action.getAllowedActions(state, useAdvancedFeatures).forEach(ac -> {
            Coord newState = ac.move(state);
            if (isValidState(newState)) {
                validNewStates.add(newState);
            }
        });
        return validNewStates;
    }


}
