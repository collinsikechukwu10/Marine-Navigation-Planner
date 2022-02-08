package core;

import strategy.SearchStrategy;

import java.util.List;

/**
 * Search Result class.
 * Used for evaluation of the search strategies implemented.
 *
 * @author 210032207
 * @version 1.0.0
 * @since 30-01-2022
 */
public class SearchResult {
    private final Coord start;
    private final Coord goal;
    private final int steps;
    private final int[][] map;
    private final SearchStrategy strategy;
    private final Node node;

    /**
     * Search Result constructor.
     *
     * @param map      problem map
     * @param start    start coordinate
     * @param goal     goal coordinate
     * @param steps    number of search steps
     * @param node     node
     * @param strategy search strategy
     */
    public SearchResult(int[][] map, Coord start, Coord goal, int steps, Node node, SearchStrategy strategy) {
        this.map = map;
        this.start = start;
        this.goal = goal;
        this.steps = steps;
        this.node = node;
        this.strategy = strategy;

    }

    /**
     * Generates a report from a list of results.
     *
     * @param results list of results
     * @return report as a string
     */
    public static String generateReport(List<SearchResult> results) {
        StringBuilder report = new StringBuilder(";strategy,mapsize,start,goal,steps,cost").append("\\n");
        results.forEach(res -> report.append(res.toString()).append("\\n"));
        return report.toString();
    }

    /**
     * @return string format of search result
     */
    @Override
    public String toString() {
        return this.strategy.name() + "," + map.length + "," + start + "," + goal + "," + steps + "," + node.getCost();
    }
}