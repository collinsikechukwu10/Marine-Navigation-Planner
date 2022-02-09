package eval;

import agent.Agent;
import core.Conf;
import core.Coord;
import core.Map;
import core.SearchResult;
import strategy.SearchStrategy;
import strategy.basic.BreadthFirstStrategy;
import strategy.basic.DepthFirstStrategy;
import strategy.intermediate.AStarStrategy;
import strategy.intermediate.BestFirstStrategy;
import strategy.intermediate.BidirectionalSearchStrategy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Search Strategy Evaluator class.
 * This class is used to evaluate all the strategies implemented.
 *
 * @author 210032207
 * @version 1.0.0
 * @since 30-01-2022
 */
public class SearchStrategyEvaluator {
    private final List<? extends SearchStrategy> searchStrategies = List.of(
            new BreadthFirstStrategy(),
            new DepthFirstStrategy(),
            new BestFirstStrategy(),
            new AStarStrategy(),
            new BidirectionalSearchStrategy());


    /**
     * Evaluates all the strategies based on the provided configurations
     */
    public void evaluateProvidedConf() {
        // evaluate all provided configurations
        List<SearchResult> result = new ArrayList<>();
        for (Conf conf : Conf.values()) {
            searchStrategies.forEach(strategy -> {
                result.add(run(conf.getMap(), conf.getS(), conf.getG(), strategy));
            });
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("eval.csv", false));
            writer.append(SearchResult.generateReport(result));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Evaluates all the strategies based on the randomly generated configurations
     */
    public void evaluateRandomConf() {
        // evaluate a large number of randomly generated maps.
        List<SearchResult> result = new ArrayList<>();
        new RandomConfGenerator().generateConfs().forEach(pr -> {
            searchStrategies.forEach(strategy -> result.add(run(pr.getMap(), pr.getStart(), pr.getGoal(), strategy, pr.toString())));
        });
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("eval.csv", false));
            writer.append(SearchResult.generateReport(result));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates an agent to run a strategy and generates a result.
     *
     * @param map      problem map
     * @param start    start coordinate
     * @param goal     goal coordinate
     * @param strategy search strategy
     * @return search result
     */
    public SearchResult run(int[][] map, Coord start, Coord goal, SearchStrategy strategy, String comment) {
        Agent agent = new Agent(map, start, goal, strategy);
        agent.traverse();
        return new SearchResult(map, start, goal, agent.getSteps(), agent.getProposedNode(), strategy, comment);
    }

    /**
     * Creates an agent to run a strategy and generates a result.
     *
     * @param map      problem map
     * @param start    start coordinate
     * @param goal     goal coordinate
     * @param strategy search strategy
     * @return search result
     */
    public SearchResult run(Map map, Coord start, Coord goal, SearchStrategy strategy) {
        return run(map.getMap(), start, goal, strategy, map.name());
    }
}
