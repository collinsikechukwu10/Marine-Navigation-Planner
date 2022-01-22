import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseStrategy {


    public abstract void populateAgenda(Deque<Path> agenda, List<Path> paths);


    public void expandPath(Deque<Path> agenda, Path currentPath, ArrayList<Coord> actions) {
        List<Path> validPaths = new ArrayList<>();
        for (Coord action : actions) {
            ArrayList<Coord> coords = new ArrayList<>(currentPath.getCoords());
            coords.add(action);
            Path path = new Path(coords, cost(currentPath, action));
            // make sure path is not cyclic
            if (!isCycle(path)) {
                validPaths.add(path);
            }
        }
        populateAgenda(agenda, validPaths);
    }

    public abstract float heuristicCost(Path oldPath, Coord action);

    public int stepCost() {
        return 1;
    }

    public float cost(Path oldPath, Coord action) {
        return oldPath.getCost() + stepCost() + heuristicCost(oldPath, action);
    }

    public abstract boolean isPathToGoal(Path path, Coord goal);

    public boolean isCycle(Path path) {
        HashMap<Coord, Boolean> counter = new HashMap<>();
        for (Coord coord : path.getCoords()) {
            if (counter.containsKey(coord)) {
                // coord already exists
                return true;
            } else {
                counter.put(coord, true);
            }
        }
        return false;
    }

    public void logFrontier(Deque<Path> frontier) {
        String frontierString = frontier.stream().map(path -> path.getState().toString()).collect(Collectors.joining(","));
        System.out.println("[" + frontierString + "]");
    }

    public void logResult(Path optimalPath, int steps) {
        if (!optimalPath.isEmpty()) {
            optimalPath.getCoords().forEach(System.out::print);
            System.out.println();
            System.out.println(optimalPath.getCost());
        } else {
            System.out.println("....");
            System.out.println("fail");
        }
        System.out.println(steps);
    }
}
