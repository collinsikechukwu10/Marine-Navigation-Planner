package core;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {
    private float cost;
    private final Coord state;
    private final Node parent;
    public static final Node FAILURE = new Node(null, null);

    public Node(Coord state, Node parent, float cost) {
        this.state = state;
        this.parent = parent;
        this.cost = cost;
    }

    public Node(Coord state, Node parent) {
        this(state, parent, 0);
    }

    public Coord getState() {
        return state;
    }

    public List<Coord> getPath() {
        ArrayList<Coord> path = new ArrayList<>(List.of(this.state));
        if (this.parent != null) {
            path.addAll(0, this.parent.getPath());
        }
        return path;
    }

    public Node getParent() {
        return parent;
    }

    public float getCost() {
        return cost;
    }

    public float getTotalStepCost() {
        return (this.parent != null) ? 1 + this.parent.getTotalStepCost() : 0;
    }

    public void printPath() {
        if (!this.hasNoPath()) {
            this.getPath().forEach(System.out::print);
            System.out.println();
            System.out.println(this.getCost());
        } else {
            System.out.println("fail");
        }
    }

    public boolean hasNoPath() {
        return state == null && parent == null;
    }

    @Override
    public int compareTo(Node node) {
        float diff = cost - node.getCost();
        return (diff == 0) ? 0 : (diff > 0) ? 1 : -1;
    }
}
