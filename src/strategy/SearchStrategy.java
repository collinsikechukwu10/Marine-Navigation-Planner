package strategy;

import core.Coord;
import core.Path;

import java.util.*;

public abstract class SearchStrategy {
    public abstract void logFrontier(Deque<Path> frontier);
    public abstract boolean isPathToGoal(Path path, Coord goal);
    public abstract float stepCost();
    public abstract float heuristicCost(Coord action, Coord goal);
    public abstract void populateAgenda(Deque<Path> agenda, List<Path> paths, Coord goal);
    public void expandPath(Deque<Path> agenda, Path currentPath, ArrayList<Coord> actions, Coord goal) {
        List<Path> validPaths = new ArrayList<>();
        for (Coord action : actions) {
            ArrayList<Coord> coords = new ArrayList<>(currentPath.getCoords());
            coords.add(action);
            float cost = currentPath.getCost() + stepCost() + heuristicCost(action, goal);
            Path path = new Path(coords, cost);
            // make sure path is not cyclic
            if (!isCycle(path)) {
                validPaths.add(path);
            }
        }
        populateAgenda(agenda, validPaths, goal);
    }
    public boolean isCycle(Path path) {
        Map<String, Boolean> counter = new HashMap<>();
        for (Coord coord : path.getCoords()) {
            if (counter.containsKey(coord.toString())) {
                // coord already exists
                return true;
            } else {
                counter.put(coord.toString(), true);
            }
        }
        return false;
    }

}
