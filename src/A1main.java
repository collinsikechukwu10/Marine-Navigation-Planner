import agent.Agent;
import agent.BidirectionalAgent;
import core.Conf;
import core.Coord;
import core.Map;
import strategy.SearchStrategy;
import strategy.basic.BreadthFirstStrategy;
import strategy.basic.DepthFirstStrategy;
import strategy.intermediate.AStarStrategy;
import strategy.intermediate.BestFirstStrategy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/********************Starter Code
 *
 * This class contains some examples on how to handle the required inputs and outputs 
 * and other debugging options
 *
 * @author at258
 *
 * run with 
 * java A1main <Algo> <ConfID> ...<additionalParameters>
 * Example: java A1main BFS JCONF03
 */
public class A1main {

    public static void main(String[] args) {
        /*
         * Retrieve input and configuration
         * and run search algorithm
         */

        String searchAlgorithm = args[0];
        Conf conf = Conf.valueOf(args[1]);
        // Additional parameters
        boolean useAdvancedFeatures = false;
        boolean verbose = false;

        if (args.length > 2) {
            List<String> additionalParameters = Arrays.stream(args).collect(Collectors.toList());
            useAdvancedFeatures = additionalParameters.contains("useAdvancedFeatures");
            verbose = additionalParameters.contains("verbose");
        }
        //run your search algorithm
        runSearch(searchAlgorithm, conf.getMap(), conf.getS(), conf.getG(), useAdvancedFeatures, verbose);

    }

    private static void runSearch(String algo, Map map, Coord start, Coord goal, boolean useAdvancedFeatures, boolean verbose) {
        SearchStrategy strategy = resolveSearchStrategy(algo);
        if (strategy == null) {
            System.out.println("Could not find search strategy associated with the one provided: [" + algo + "]");
            System.exit(0);
        }
        Agent agent;
        if (algo.equals("BDS")) {
            agent = new BidirectionalAgent(map.getMap(), start, goal, strategy);
        } else {
            agent = new Agent(map.getMap(), start, goal, strategy);
        }
        agent.setUseAdvancedFeatures(useAdvancedFeatures);
        agent.setVerbose(verbose);
        agent.traverse();
    }

    private static SearchStrategy resolveSearchStrategy(String searchStrategyString) {
        SearchStrategy strategy = null;
        switch (searchStrategyString) {
            case "BFS": //run BFS
            case "BDS": // For bidirectional search
                strategy = new BreadthFirstStrategy();
                break;
            case "DFS": //run DFS
                strategy = new DepthFirstStrategy();
                break;
            case "BestF": //run BestF
                strategy = new BestFirstStrategy();
                break;
            case "AStar": //run AStar
                strategy = new AStarStrategy();
                break;
        }
        return strategy;
    }


    private static void printMap(Map m, Coord init, Coord goal) {

        int[][] map = m.getMap();

        System.out.println();
        int rows = map.length;
        int columns = map[0].length;

        //top row
        System.out.print("  ");
        for (int c = 0; c < columns; c++) {
            System.out.print(" " + c);
        }
        System.out.println();
        System.out.print("  ");
        for (int c = 0; c < columns; c++) {
            System.out.print(" -");
        }
        System.out.println();

        //print rows
        for (int r = 0; r < rows; r++) {
            boolean right;
            System.out.print(r + "|");
            //even row, starts right [=starts left & flip right] while
            //odd row, starts left [=starts right & flip left]
            right = r % 2 != 0;
            for (int c = 0; c < columns; c++) {
                System.out.print(flip(right));
                if (isCoord(init, r, c)) {
                    System.out.print("S");
                } else if (isCoord(goal, r, c)) {
                    System.out.print("G");
                } else if (map[r][c] == 0) {
                    System.out.print(".");
                } else {
                    System.out.print(map[r][c]);
                }
                right = !right;
            }
            System.out.println(flip(right));
        }
        System.out.println();
    }

    /**
     * Check if coordinates are the same as current (r,c)
     *
     * @param coord coordinate
     * @param r     row
     * @param c     column
     * @return True if coordinates are the same
     */
    private static boolean isCoord(Coord coord, int r, int c) {
        return coord.getR() == r && coord.getC() == c;
    }

    /**
     * Prints triangle edges. right return left and vice versa
     *
     * @param right is forward slash
     * @return flipped slash
     */
    public static String flip(boolean right) {
        //
        return (right) ? "\\" : "/";

    }

}
