package strategy.intermediate;

import core.Coord;
import core.Path;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class AStarStrategy extends IntermediateStrategy {
    @Override
    public void populateAgenda(Deque<Path> agenda, List<Path> paths, Coord goal) {
        // add paths to agenda
        agenda.addAll(paths);
        // sort all paths in agenda, order by descending order of heuristic cost, so the lowest cost gets popped first
        agenda = agenda.stream().sorted(
//                (p1, p2) -> (int) Math.ceil(cost(p1.getState(), goal) - heuristicCost(p2.getState(), goal))
        ).collect(Collectors.toCollection(ArrayDeque::new));
    }

    private float cost(Path currentPath, Coord action, Coord goal){
        return currentPath.getCost() + stepCost() + heuristicCost(action,goal);
    }


    @Override
    public boolean isPathToGoal(Path path, Coord goal) {
        return path != null && path.getState().equals(goal);
    }
}
