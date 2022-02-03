package agent;

import core.Coord;
import core.Node;
import strategy.SearchStrategy;
import strategy.basic.BreadthFirstStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BidirectionalAgent extends Agent {

    /**
     * Constructor for agent class.
     *
     * @param map   map to traverse
     * @param start departure or start point
     * @param goal  destination or end point
     */
    public BidirectionalAgent(int[][] map, Coord start, Coord goal, SearchStrategy strategy) {
        super(map, start, goal, strategy);
    }

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
//                System.out.println(getCoordsFromNodes(forwardVisited));
                // perform forward step for both back ward and forward bfs
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

    public Node extractIntersectingPath(List<Node> forwardNodeList, List<Node> backwardNodeList) {
        List<Coord> forwardCoords = forwardNodeList.stream().map(Node::getState).collect(Collectors.toList());
        List<Coord> backwardCoords = backwardNodeList.stream().map(Node::getState).collect(Collectors.toList());
        forwardCoords.retainAll(backwardCoords);
        // only get the first coordinate if intersection exists.
        if (forwardCoords.size() > 0) {
            Coord intersection = new ArrayList<>(forwardCoords).get(0);
            System.out.println("Found intersecting coordinate at " + intersection);
            Optional<Node> primaryNodeOptional = forwardNodeList.stream().filter(nd -> nd.getState().equals(intersection)).findFirst();
            Optional<Node> backwardNodeOptional = backwardNodeList.stream().filter(nd -> nd.getState().equals(intersection)).findFirst();
            if (primaryNodeOptional.isPresent() && backwardNodeOptional.isPresent()) {
                Node optimalNode = primaryNodeOptional.get();
                Node backwardNode = backwardNodeOptional.get();
                // to prevent combining the same node twice, we read from the parent of the primary node
                optimalNode = optimalNode.getParent();
                while (backwardNode != null) {
                    Coord newState = backwardNode.getState();
                    optimalNode = new Node(newState, optimalNode, strategy.cost(optimalNode, newState, goal));
                    backwardNode = backwardNode.getParent();
                }
                return optimalNode;
            }
        }
        return null;
    }
}
