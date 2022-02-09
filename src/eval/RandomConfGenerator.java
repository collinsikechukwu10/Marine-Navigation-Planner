package eval;

import core.Coord;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Random Conf Generator class.
 * Used for generate random maps, starts and goals for search strategy evaluation.
 *
 * @author 210032207
 * @version 1.0.0
 * @since 30-01-2022
 */
public class RandomConfGenerator {
    private static final int NO_RANDOM_MAPS = 50;
    private static final float landRatio = (float) 0.1;
    private static final int MAX_MAP_SIZE = 50;
    private static final int MIN_MAP_SIZE = 3;
    private static final long SEED = 214;

    private static final int RETRY_LIMIT = 4;

    /**
     * Random Cong generator constructor
     */
    public RandomConfGenerator() {
        ThreadLocalRandom.current().setSeed(SEED);
    }

    /**
     * @return randomly generated configurations
     */
    public List<Problem> generateConfs() {
        List<Problem> problem = new ArrayList<>();

        for (int i = 0; i < NO_RANDOM_MAPS; i++) {
            // generate map
            int mapSize = getRandomInt(MIN_MAP_SIZE, MAX_MAP_SIZE);
            int[][] generatedMap = new int[mapSize][mapSize];

            // generate start and goal coordinates
            int startR = getRandomInt(MIN_MAP_SIZE - 1, mapSize - 1);
            int goalR = getRandomInt(MIN_MAP_SIZE - 1, mapSize - 1);
            int startC = getRandomInt(MIN_MAP_SIZE - 1, mapSize - 1);
            int goalC = getRandomInt(MIN_MAP_SIZE - 1, mapSize - 1);
            Coord start = new Coord(startR, startC);
            Coord goal = new Coord(goalR, goalC);

            // generate random land points
            float noOfLandCoords = (int) (landRatio * mapSize * mapSize);
            for (int j = 0; j < noOfLandCoords; j++) {
                generateRandomLand(generatedMap, start, goal);
            }
            problem.add(new Problem(generatedMap, start, goal));
        }
        return problem;
    }

    /**
     * Generates rando integer between a min and max value provided.
     *
     * @param min minimum value
     * @param max maximum value
     * @return random integer
     */
    public int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * Creates a random land on a map.
     *
     * @param map   map
     * @param start start coordinate
     * @param goal  goal coordinate
     */
    private void generateRandomLand(int[][] map, Coord start, Coord goal) {
        generateRandomLand(map, start, goal, RETRY_LIMIT);
    }

    /**
     * Creates a random land on a map.
     * A retry number is specified in case the position for the land is already occupied, is a start or is a goal
     *
     * @param map     map
     * @param start   start coordinate
     * @param goal    goal coordinate
     * @param retries number of retries
     */
    private void generateRandomLand(int[][] map, Coord start, Coord goal, int retries) {
        int mapSize = map.length;
        int r = getRandomInt(MIN_MAP_SIZE - 1, mapSize - 1);
        int c = getRandomInt(MIN_MAP_SIZE - 1, mapSize - 1);
        Coord coord = new Coord(r, c);
        if (map[r][c] == 0 && !coord.equals(start) && !coord.equals(goal)) {
            map[r][c] = 1;
        } else {
            if (!(retries < 1)) {
                generateRandomLand(map, start, goal, retries - 1);
            }
        }
    }

    /**
     * Problem class.
     * Has the same functionality as the `Conf` Enum.
     * Provides access to the map, start and goal information.
     *
     * @author 210032207
     * @version 1.0.0
     * @since 30-01-2022
     */
    public static class Problem {
        public int[][] map;
        public Coord start;
        public Coord goal;

        /**
         * Problem constructor specifying the map, start coordinate and goal coordinate.
         *
         * @param map   problem map
         * @param start start coordinate
         * @param goal  goal coordinate
         */
        Problem(int[][] map, Coord start, Coord goal) {
            this.map = map;
            this.start = start;
            this.goal = goal;
        }

        /**
         * @return problem map
         */
        public int[][] getMap() {
            return map;
        }

        /**
         * @return start coordinate
         */
        public Coord getStart() {
            return start;
        }

        /**
         * @return goal coordinate
         */
        public Coord getGoal() {
            return goal;
        }
    }
}
