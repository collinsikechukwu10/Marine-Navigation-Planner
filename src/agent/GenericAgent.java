package agent;

import core.Coord;
import core.Node;
import strategy.SearchStrategy;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Generic Agent Abstract Class.
 * All agents must implement this class.
 * This class is used to traverse the map using any of the implemented strategies subclassed by `SearchStrategy`
 * All subclasses must implement the `traverse` function
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
public abstract class GenericAgent {
    protected final Coord start;
    protected final Coord goal;
    protected final int[][] map;
    protected final SearchStrategy strategy;
    protected ArrayDeque<Node> frontier = new ArrayDeque<>();
    protected ArrayList<Node> explored = new ArrayList<>();
    protected int steps;
    protected Node proposedNode;
    protected boolean verbose;
    protected boolean useAdvancedFeatures;

    /**
     * Constructor for agent class.
     *
     * @param map   map to traverse
     * @param start departure or start point
     * @param goal  destination or end point
     */
    public GenericAgent(int[][] map, Coord start, Coord goal, SearchStrategy strategy) {
        this.map = map;
        this.start = start;
        this.goal = goal;
        this.strategy = strategy;
        this.useAdvancedFeatures = false;
        this.verbose = false;
        this.steps = 0;
        this.proposedNode = Node.FAILURE;
    }

    /**
     * Traverse the map provided from the departure/start point to the destination/end point.
     */
    public abstract void traverse();

    /**
     * @return steps
     */
    public int getSteps() {
        return steps;
    }

    /**
     * Sets the steps
     *
     * @param steps the steps to set
     */
    public void setSteps(int steps) {
        this.steps = steps;
    }

    /**
     * @return proposed node
     */
    public Node getProposedNode() {
        return proposedNode;
    }

    /**
     * Sets the proposed node
     *
     * @param proposedNode the proposed node to set
     */
    public void setProposedNode(Node proposedNode) {
        this.proposedNode = proposedNode;
    }

    /**
     * Set the verbosity of the agent.
     *
     * @param verbose verbosity
     */
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * Option to use/ not use advanced features implemented
     *
     * @param useAdvancedFeatures option to toggle use of advanced features.
     */
    public void setUseAdvancedFeatures(boolean useAdvancedFeatures) {
        this.useAdvancedFeatures = useAdvancedFeatures;
    }

    /**
     * @return frontier
     */
    public ArrayDeque<Node> getFrontier() {
        return frontier;
    }


    /**
     * @return explored nodes
     */
    public ArrayList<Node> getExplored() {
        return explored;
    }

    /**
     * Sets the explored nodes
     *
     * @param explored explored nodes to set
     */
    public void setExplored(ArrayList<Node> explored) {
        this.explored = explored;
    }

    /**
     * Log the path and the steps taken to get to a node.
     *
     * @param node  node to get path from
     * @param steps number of steps taken to get to the node
     */
    public void logSearchResult(Node node, int steps) {
        node.printPath();
        System.out.println(steps);
    }

}
