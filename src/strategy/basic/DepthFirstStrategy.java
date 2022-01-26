package strategy.basic;

import core.Coord;
import core.Path;

import java.util.Deque;
import java.util.List;

public class DepthFirstStrategy extends BasicStrategy {


    @Override
    public void populateAgenda(Deque<Path> agenda, List<Path> paths, Coord goal) {
    // ordering of paths is currently at decreasing levels of action priority as defined by the tie-breaking strategy
        for (int i = paths.size()-1; i >=0 ; i--) {
            agenda.addFirst(paths.get(i));
        }

    }
}