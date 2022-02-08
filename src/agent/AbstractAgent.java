package agent;

import core.Coord;
import core.Map;
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
public abstract class AbstractAgent {
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
    public AbstractAgent(int[][] map, Coord start, Coord goal, SearchStrategy strategy) {
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

    /**
     * Prints the problem map
     */
    public void printMap() {
        System.out.println();
        int rows = map.length;
        int columns = map[0].length;

        //top row
        System.out.print("  ");
        for (int c = 0; c < columns; c++) {
            System.out.print(" " + c);
        }
        System.out.println();
        System.out.print("  ");
        for (int c = 0; c < columns; c++) {
            System.out.print(" -");
        }
        System.out.println();

        //print rows
        for (int r = 0; r < rows; r++) {
            boolean right;
            System.out.print(r + "|");
            //even row, starts right [=starts left & flip right] while
            //odd row, starts left [=starts right & flip left]
            right = r % 2 != 0;
            for (int c = 0; c < columns; c++) {
                System.out.print(flip(right));
                if (isCoord(start, r, c)) {
                    System.out.print("S");
                } else if (isCoord(goal, r, c)) {
                    System.out.print("G");
                } else if (map[r][c] == 0) {
                    System.out.print(".");
                } else {
                    System.out.print(map[r][c]);
                }
                right = !right;
            }
            System.out.println(flip(right));
        }
        System.out.println();
    }
    /**
     * Check if coordinates are the same as current (r,c)
     *
     * @param coord coordinate
     * @param r     row
     * @param c     column
     * @return True if coordinates are the same
     */
    private boolean isCoord(Coord coord, int r, int c) {
        return coord.getR() == r && coord.getC() == c;
    }

    /**
     * Prints triangle edges. right return left and vice versa
     *
     * @param right is forward slash
     * @return flipped slash
     */
    private String flip(boolean right) {
        return (right) ? "\\" : "/";
    }

}
