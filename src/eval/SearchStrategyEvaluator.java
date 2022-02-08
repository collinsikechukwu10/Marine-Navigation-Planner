package eval;

import agent.Agent;
import core.Conf;
import core.Coord;
import core.SearchResult;
import strategy.SearchStrategy;
import strategy.basic.BreadthFirstStrategy;
import strategy.basic.DepthFirstStrategy;
import strategy.intermediate.AStarStrategy;
import strategy.intermediate.BestFirstStrategy;

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


    public static void main(String[] args) {
        evaluate();
    }

    /**
     * Evaluates all the strategies based on the provided configurations and a large number of random configurations
     */
    private static void evaluate() {
        // evaluate all provided configurations
        List<? extends SearchStrategy> searchStrategies = List.of(
                new BreadthFirstStrategy(),
                new DepthFirstStrategy(),
                new BestFirstStrategy(),
                new AStarStrategy());
        List<SearchResult> result = new ArrayList<>();
        for (Conf conf : Conf.values()) {
            searchStrategies.forEach(strategy -> {
                result.add(run(conf.getMap().getMap(), conf.getS(), conf.getG(), strategy));
            });
        }

        // evaluate a large number of randomly generated maps.
        new RandomConfGenerator().generateConfs().forEach(pr -> {
            searchStrategies.forEach(strategy -> result.add(run(pr.getMap(), pr.getStart(), pr.getGoal(), strategy)));
        });
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("eval.csv", true));
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
    public static SearchResult run(int[][] map, Coord start, Coord goal, SearchStrategy strategy) {
        Agent agent = new Agent(map, start, goal, strategy);
        agent.traverse();
        return new SearchResult(map, start, goal, agent.getSteps(), agent.getProposedNode(), strategy);
    }
}