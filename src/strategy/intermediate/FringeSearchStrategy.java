package strategy.intermediate;

import core.Coord;
import core.Node;

import java.util.*;

public class FringeSearchStrategy extends IntermediateStrategy {

    Map<Coord, Node> cache = new HashMap<>();
    @Override
    public void orderFrontier(Deque<Node> frontier, List<Node> nodes, Coord goal) {

    }

    public Node extractPath(Map<Coord,Node> cache, Coord start){
        Coord currentState = start;
        Node node = new Node(start,null);
        while (currentState != null){
//            Node parent
//            node = new Node(cache.get(currentState),node,cost(node,))
        }
        return node;
    }




    @Override
    public void expandNode(Deque<Node> frontier, List<Node> explored, Node currentNode, ArrayList<Coord> newStates, Coord goal) {

        List<Node> validNodes = new ArrayList<>();
        for (Coord newState : newStates) {
            Node node = new Node(newState, currentNode, cost(currentNode, newState, goal));
            boolean stateExplored = stateExistsInNodes(explored, newState);
            boolean stateInFrontier = stateExistsInNodes(new ArrayList<>(frontier), newState);
            // remove state from frontier if it exists
//            frontier = frontier.stream().filter(n->n.getState() != newState).collect(Collectors.toCollection(ArrayDeque::new))

            if (!stateExplored && !stateInFrontier) {
                validNodes.add(node);
            }
        }
        orderFrontier(frontier, validNodes, goal);
    }

    @Override
    public float cost(Node previousNode, Coord newState, Coord goal) {
        return 0;
    }
}
