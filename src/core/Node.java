package core;

import java.util.ArrayList;
import java.util.List;

/**
 * Node class.
 *
 * @author 210032207
 * @version 1.0.0
 * @since 30-01-2022
 */
public class Node implements Comparable<Node> {
    public static final Node FAILURE = new Node(null, null);
    private final float cost;
    private final Coord state;
    private final Node parent;

    /**
     * Constructor specifying the state parent node and the cost.
     *
     * @param state  current state of the node
     * @param parent parent node
     * @param cost   cost of the being in the current state
     */
    public Node(Coord state, Node parent, float cost) {
        this.state = state;
        this.parent = parent;
        this.cost = cost;
    }

    /**
     * Constructor specifying the state parent node and the cost.
     * Defaults cost to 0.
     *
     * @param state  current state of the node
     * @param parent parent node
     */
    public Node(Coord state, Node parent) {
        this(state, parent, 0);
    }

    /**
     * @return current state
     */
    public Coord getState() {
        return state;
    }

    /**
     * @return path the node has traversed.
     */
    public List<Coord> getPath() {
        ArrayList<Coord> path = new ArrayList<>(List.of(this.state));
        if (this.parent != null) {
            path.addAll(0, this.parent.getPath());
        }
        return path;
    }

    /**
     * @return parent node
     */
    public Node getParent() {
        return parent;
    }

    /**
     * @return cost of the node
     */
    public float getCost() {
        return cost;
    }

    /**
     * Calculates the total number of steps taken to reach the current node.
     *
     * @return number of steps taken to reach current node
     */
    public float getTotalStepCost() {
        return (this.parent != null) ? 1 + this.parent.getTotalStepCost() : 0;
    }

    /**
     * Prints the path and cost that a node has traversed.
     */
    public void printPath() {
        if (!this.hasNoPath()) {
            this.getPath().forEach(System.out::print);
            System.out.println();
            System.out.println(this.getCost());
        } else {
            System.out.println("fail");
        }
    }

    /**
     * Checks if a path exists in this node.
     *
     * @return True if path exists
     */
    public boolean hasNoPath() {
        return state == null && parent == null;
    }

    @Override
    public int compareTo(Node node) {
        float diff = cost - node.getCost();
        return (diff == 0) ? 0 : (diff > 0) ? 1 : -1;
    }
}
