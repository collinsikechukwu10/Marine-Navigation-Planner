package strategy.basic;

import core.Coord;
import core.Node;

import java.util.Deque;
import java.util.List;

/**
 * Depth First Search Strategy Abstract class.
 *
 * @author 210032207
 * @version 1.0.0
 * @since 30-01-2022
 */
public class DepthFirstStrategy extends BasicStrategy {
    /**
     * Appends nodes and orders nodes in a frontier.
     *
     * @param frontier search frontier/agenda
     * @param nodes    new nodes
     * @param goal     goal of the search problem
     */
    @Override
    public void orderFrontier(Deque<Node> frontier, List<Node> nodes, Coord goal) {
        // ordering of paths is currently at decreasing levels of action priority as defined by the tie-breaking strategy
        for (int i = nodes.size() - 1; i >= 0; i--) {
            frontier.addFirst(nodes.get(i));
        }

    }
}