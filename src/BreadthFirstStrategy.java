import java.util.Deque;
import java.util.List;

public class BreadthFirstStrategy extends BaseStrategy {


    @Override
    public void populateAgenda(Deque<Path> agenda, List<Path> paths) {
        // ordering of paths is currently at decreasing levels of action priority as defined by the tie-breaking strategy
        for (int i = 0; i <paths.size() ; i++) {
            agenda.addFirst(paths.get(i));
        }
    }

    @Override
    public float heuristicCost(Path oldPath, Coord action) {
        return 0;
    }

    @Override
    public boolean isPathToGoal(Path path, Coord goal) {
        return path != null && path.getState().equals(goal);
    }
}
