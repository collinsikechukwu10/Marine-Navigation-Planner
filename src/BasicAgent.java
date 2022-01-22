import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;


public class BasicAgent {
    Coord start;
    Coord goal;
    Map map;
    int cost;
    int height;
    int width;
    private static final int LAND = 1;
    BaseStrategy strategy;

    private static class Action {
        static Coord moveRight(Coord coord) {
            return new Coord(coord.getR(), coord.getC() + 1);
        }

        static Coord moveLeft(Coord coord) {
            return new Coord(coord.getR(), coord.getC() - 1);
        }

        static Coord moveUp(Coord coord) {
            return new Coord(coord.getR() - 1, coord.getC());
        }

        static Coord moveDown(Coord coord) {
            return new Coord(coord.getR() + 1, coord.getC());
        }
    }


    public BasicAgent(Map map, Coord start, Coord goal) {
        this.map = map;
        this.start = start;
        this.goal = goal;
        this.cost = 0;
        this.height = map.getMap().length;
        this.width = map.getMap()[0].length;

    }

    public void setSearchStrategy(BaseStrategy strategy) {
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
            Path currentPath = agenda.pollLast();
            // calculate cost
            if (strategy.isPathToGoal(currentPath, goal)) {
                optimalPath = currentPath;
                break;
            }
            if (!traversed.contains(currentPath.getState())) {
                traversed.add(currentPath.getState());
                ArrayList<Coord> validActions = checkAvailableActions(currentPath.getState());
                strategy.expandPath(agenda, currentPath, validActions);
            }
        }
        strategy.logResult(optimalPath, steps);
    }

    private boolean isLand(Coord coord) {
        return this.map.getMap()[coord.getR()][coord.getC()] == LAND;
    }

    private boolean isOutOfBounds(Coord coord) {
        int r = coord.getR();
        int c = coord.getC();
        return r < 0 || r >= height || c < 0 || c >= width;
    }

    private ArrayList<Coord> checkAvailableActions(Coord coord) {
        // Triangles pointing upwards only exists at points where r+c is even
        // Actions are arranged in order of priority
        List<Coord> allActions;
        ArrayList<Coord> availableActions = new ArrayList<>();
        if (coord.getR() + coord.getC() % 2 == 0) {
            // upwards facing triangle
            allActions = List.of(Action.moveRight(coord), Action.moveLeft(coord), Action.moveUp(coord));
        } else {
            //downward facing triangle
            allActions = List.of(Action.moveRight(coord), Action.moveDown(coord), Action.moveLeft(coord));
        }
        allActions.forEach(ac -> {
            if (!isOutOfBounds(ac) && !isLand(ac)) {
                availableActions.add(ac);
            }
        });
        return availableActions;
    }

}
