package agent;

import core.Coord;
import core.Node;
import strategy.SearchStrategy;
import strategy.basic.BreadthFirstStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Bidirectional Agent Class.
 * This class is used for bidirectional search.
 *
 * @author 210032207
 * @version 1.0.0
 * @since 30-01-2022
 */
public class BidirectionalAgent extends Agent {

    /**
     * Constructor for the bidirectional agent class.
     *
     * @param map   map to traverse
     * @param start departure or start point
     * @param goal  destination or end point
     */
    public BidirectionalAgent(int[][] map, Coord start, Coord goal, SearchStrategy strategy) {
        super(map, start, goal, strategy);
    }

    /**
     * Traverse the map provided from the departure/start point to the destination/end point.
     */
    @Override
    public void traverse() {
        if (strategy instanceof BreadthFirstStrategy) {
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
        } else {
            System.out.println("Cannot use `" + strategy.name() + "` for bidirectional search, please use BFS");
        }
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
