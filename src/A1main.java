import agent.Agent;
import core.Conf;
import core.Coord;
import core.Map;
import eval.SearchStrategyEvaluator;
import strategy.SearchStrategy;
import strategy.basic.BreadthFirstStrategy;
import strategy.basic.DepthFirstStrategy;
import strategy.intermediate.AStarStrategy;
import strategy.intermediate.BestFirstStrategy;
import strategy.intermediate.BidirectionalSearchStrategy;

import java.util.Objects;

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
    /**
     * Entry point for the search algorithm
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        /*
         * Retrieve input and configuration
         * and run search algorithm
         */
        String searchAlgorithm = args[0];
        if (Objects.equals(searchAlgorithm, "eval")) {
            SearchStrategyEvaluator evaluator = new SearchStrategyEvaluator();
            if (args.length > 1 && Objects.equals(args[1], "randomMap")) {
                evaluator.evaluateRandomConf();
            } else {
                evaluator.evaluateProvidedConf();
            }
            System.exit(0);
        } else {
            boolean useAdvancedMoves = false;
            if (args.length > 1) {
                Conf conf = Conf.valueOf(args[1]);
                // Additional parameters
                if (args.length > 2) {
                    useAdvancedMoves = Objects.equals(args[0], "useAdvancedMoves");
                }
                runSearch(searchAlgorithm, conf.getMap(), conf.getS(), conf.getG(), useAdvancedMoves);
                System.exit(0);
            }
        }
        System.out.println("Please provide parameters");
        System.out.println("java A1main <Algo> <ConfID> ...<additionalParameters>");

    }

    /**
     * Run search algorithm
     *
     * @param algo             search algorithm strategy
     * @param map              search problem map
     * @param start            start coordinate
     * @param goal             goal coordinate
     * @param useAdvancedMoves use advanced moves
     */
    private static void runSearch(String algo, Map map, Coord start, Coord goal, boolean useAdvancedMoves) {
        SearchStrategy strategy = searchStrategyFactory(algo);
        if (strategy == null) {
            System.out.println("Could not find search strategy associated with the one provided: [" + algo + "]");
            System.exit(0);
        }
        Agent agent = new Agent(map.getMap(), start, goal, strategy);
        agent.setUseAdvancedMoves(useAdvancedMoves);
        agent.traverse();
    }

    /**
     * Resolves search strategy type base on a string
     *
     * @param searchStrategyString search strategy type
     * @return Search strategy subclass instance
     */
    private static SearchStrategy searchStrategyFactory(String searchStrategyString) {
        SearchStrategy strategy = null;
        switch (searchStrategyString) {
            case "BFS": //run BFS
                strategy = new BreadthFirstStrategy();
                break;
            case "BDS": // For bidirectional search
                strategy = new BidirectionalSearchStrategy();
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

}
