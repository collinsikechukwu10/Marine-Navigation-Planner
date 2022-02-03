package eval;

import agent.Agent;
import core.Conf;
import core.Coord;
import core.Node;
import strategy.SearchStrategy;
import strategy.basic.BreadthFirstStrategy;
import strategy.basic.DepthFirstStrategy;
import strategy.intermediate.AStarStrategy;
import strategy.intermediate.BestFirstStrategy;

import java.util.ArrayList;
import java.util.List;

public class SearchStrategyTester {

    private static class SearchResult {
        private final Coord start;
        private final Coord goal;
        private final int steps;
        private final Node proposedNode;
        private final int[][] map;

        SearchResult(int[][] map, Coord start, Coord goal, int steps, Node proposedNode) {
            this.map = map;
            this.start = start;
            this.goal = goal;
            this.steps = steps;
            this.proposedNode = proposedNode;

        }

        @Override
        public String toString() {
            int mapLength = this.map.length;
            int mapwidth = this.map[0].length;
            return "(" + mapLength + "," + mapwidth + "), " + start + "," + goal + "," + steps;
        }
    }

    public static void main(String[] args) {
        String searchAlgorithm = args[0];
        run(resolveSearchStrategy(searchAlgorithm));
    }

    public void testProvidedMaps(){
        for (Conf conf:Conf.values()){

        }
    }

    private List<SearchStrategy> getAllImplementedStrategies(){
        return null;
    }

    public static void run(SearchStrategy strategy) {
        ArrayList<SearchResult> resultList = new ArrayList<>();
        new MapGenerator().generateProblems().forEach(pr -> {
            int[][] map = pr.getMap();
            Coord start = pr.getStart();
            Coord goal = pr.getGoal();
            System.out.println("Running Map..... size:[" + map.length + "," + map[0].length + "]" +
                    ", start:" + start + ", goal:" + goal);
            Agent agent = new Agent(map, start, goal, strategy);
            agent.traverse();
            resultList.add(new SearchResult(map, start, goal, agent.getSteps(), agent.getProposedNode()));
        });
        // log results
        resultList.forEach(System.out::println);

    }


    private static void printMap(int[][] map, Coord init, Coord goal) {
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
            right = !(r % 2 == 0);
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

    private static boolean isCoord(Coord coord, int r, int c) {
        //check if coordinates are the same as current (r,c)
        return coord.getR() == r && coord.getC() == c;
    }


    public static String flip(boolean right) {
        //prints triangle edges, right return left
        return (right) ? "\\" : "/";
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

}
