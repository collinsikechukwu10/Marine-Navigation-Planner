package strategy.basic;

import core.Coord;
import core.Node;

import java.util.Deque;
import java.util.List;

public class BreadthFirstStrategy extends BasicStrategy {

    @Override
    public void orderFrontier(Deque<Node> agenda, List<Node> nodes, Coord goal) {
        // ordering of paths is currently at decreasing levels of action priority as defined by the tie-breaking strategy
        for (int i = 0; i < nodes.size(); i++) {
            agenda.addLast(nodes.get(i));
        }
    }

}
