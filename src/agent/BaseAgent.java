package agent;

import core.Coord;
import core.Node;
import strategy.SearchStrategy;

import java.util.ArrayDeque;
import java.util.ArrayList;

public abstract class BaseAgent {
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


    public BaseAgent(int[][] map, Coord start, Coord goal, SearchStrategy strategy) {
        this.map = map;
        this.start = start;
        this.goal = goal;
        this.strategy = strategy;
        this.useAdvancedFeatures = false;
        this.verbose = false;
        this.steps = 0;
        this.proposedNode = Node.FAILURE;
    }

    public abstract void traverse();

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public Node getProposedNode() {
        return proposedNode;
    }

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
     * @param useAdvancedFeatures option to toogle use of advanced features.
     */
    public void setUseAdvancedFeatures(boolean useAdvancedFeatures) {
        this.useAdvancedFeatures = useAdvancedFeatures;
    }

    public ArrayDeque<Node> getFrontier() {
        return frontier;
    }

    public void setFrontier(ArrayDeque<Node> frontier) {
        this.frontier = frontier;
    }

    public ArrayList<Node> getExplored() {
        return explored;
    }

    public void setExplored(ArrayList<Node> explored) {
        this.explored = explored;
    }

    public void logSearchResult(Node node, int steps) {
        node.printPath();
        System.out.println(steps);
    }

}
