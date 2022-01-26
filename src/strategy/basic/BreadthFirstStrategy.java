package strategy.basic;

import core.Coord;
import core.Path;

import java.util.Deque;
import java.util.List;

public class BreadthFirstStrategy extends BasicStrategy {

    @Override
    public void populateAgenda(Deque<Path> agenda, List<Path> paths, Coord goal) {
        // ordering of paths is currently at decreasing levels of action priority as defined by the tie-breaking strategy
        for (int i = 0; i < paths.size(); i++) {
            agenda.addLast(paths.get(i));
        }
    }

}
