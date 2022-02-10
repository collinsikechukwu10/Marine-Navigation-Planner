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
    private final String comment;

    /**
     * Search Result constructor.
     *
     * @param map      problem map
     * @param start    start coordinate
     * @param goal     goal coordinate
     * @param steps    number of search steps
     * @param node     node
     * @param strategy search strategy
     * @param comment  any extra information about a search result
     */
    public SearchResult(int[][] map, Coord start, Coord goal, int steps, Node node, SearchStrategy strategy, String comment) {
        this.map = map;
        this.start = start;
        this.goal = goal;
        this.steps = steps;
        this.node = node;
        this.strategy = strategy;
        this.comment = comment;

    }

    /**
     * Calculates euclidean distance between two points
     *
     * @param point1 first coordinate
     * @param point2 second coordinate
     * @return euclidean distance of two points
     */
    private float euclideanDistance(Coord point1, Coord point2) {
        return (float) Math.sqrt(
                Math.pow(Math.abs(point1.getR() - point2.getR()), 2) +
                        Math.pow(Math.abs(point1.getC() - point2.getC()), 2));
    }

    /**
     * Generates a report from a list of results.
     *
     * @param results list of results
     * @return report as a string
     */
    public static String generateReport(List<SearchResult> results) {
        StringBuilder report = new StringBuilder("strategy,mapSize,start goal distance,steps,cost,comments").append("\n");
        results.forEach(res -> report.append(res.toString()).append("\n"));
        return report.toString();
    }

    /**
     * @return string format of search result
     */
    @Override
    public String toString() {
        return this.strategy.name() + "," + map.length + "," + euclideanDistance(start, goal) + "," +
                steps + "," + node.getTotalStepCost() + "," + comment;
    }
}