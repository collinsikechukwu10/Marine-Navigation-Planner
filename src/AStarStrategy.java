import java.util.Deque;
import java.util.List;

public class AStarStrategy extends IntermediateStrategy{
    @Override
    public void populateAgenda(Deque<Path> agenda, List<Path> paths) {

    }

    @Override
    public float heuristicCost(Path oldPath, Coord action) {
        return 0;
    }

    @Override
    public boolean isPathToGoal(Path path, Coord goal) {
        return false;
    }
}
