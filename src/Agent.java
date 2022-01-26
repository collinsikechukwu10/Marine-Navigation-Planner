import core.Action;
import core.Coord;
import core.Map;
import core.Path;
import strategy.SearchStrategy;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;


public class Agent {
    private final Coord start;
    private final Coord goal;
    private final Map map;
    private final int mapSize;
    private final boolean useAdvancedFeatures;
    private static final int LAND = 1;
    private SearchStrategy strategy;



    public Agent(Map map, Coord start, Coord goal) {
        this.map = map;
        this.start = start;
        this.goal = goal;
        this.mapSize = map.getMap().length;
        this.useAdvancedFeatures = false;
    }

    public void setSearchStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
    }


    public void traverse() {
        // Here i am taking the front of the agenda as the highest index (size -1)
        // and the back of the agenda as the lowest index (0).
        ArrayDeque<Path> agenda = new ArrayDeque<>();
        List<Coord> traversed = new ArrayList<>();
        // add start point to agenda
        agenda.add(new Path(List.of(start), 0));
        int steps = 0;
        Path optimalPath = Path.None();

        while (!agenda.isEmpty()) {
            strategy.logFrontier(agenda);
            steps++;
            Path currentPath = agenda.pollFirst();
            if (strategy.isPathToGoal(currentPath, goal)) {
                optimalPath = currentPath;
                break;
            }
            if (!traversed.contains(currentPath.getState())) {
                traversed.add(currentPath.getState());
                ArrayList<Coord> validActions = checkAvailableActions(currentPath.getState());
                strategy.expandPath(agenda, currentPath, validActions, goal);
            }
        }
        logResult(optimalPath, steps);
    }

    private boolean isLand(Coord coord) {
        return this.map.getMap()[coord.getR()][coord.getC()] == LAND;
    }

    private boolean isOutOfBounds(Coord coord) {
        int r = coord.getR();
        int c = coord.getC();
        return r < 0 || r >= mapSize || c < 0 || c >= mapSize;
    }

    private ArrayList<Coord> checkAvailableActions(Coord coord) {
        // Triangles pointing upwards only exists at points where r+c is even
        // Actions are arranged in order of priority
        ArrayList<Coord> availableActions = new ArrayList<>();
        boolean upwardsFacingTriangle = (coord.getR() + coord.getC()) % 2 == 0;
        Action.getAllowedActions(upwardsFacingTriangle, useAdvancedFeatures).forEach(ac->{
            Coord newState = ac.move(coord);
            if (!isOutOfBounds(newState) && !isLand(newState)) {
                availableActions.add(newState);
            }
        });
        return availableActions;
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
