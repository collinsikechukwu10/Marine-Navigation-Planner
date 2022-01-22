import java.util.Deque;
import java.util.stream.Collectors;

public abstract class IntermediateStrategy extends BaseStrategy {
    @Override
    public void logFrontier(Deque<Path> frontier) {
        String frontierString = frontier.stream().map(path -> path.getState().toString() + ":" + path.getCost()).collect(Collectors.joining(","));
        System.out.println("[" + frontierString + "]");
    }
}
