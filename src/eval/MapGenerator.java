package eval;

import core.Coord;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MapGenerator {
    private static final int NO_RANDOM_MAPS = 50;
    private static final float landRatio = (float) 0.1;
    private static final int MAX_MAP_SIZE = 50;
    private static final int MIN_MAP_SIZE = 3;

    private static final int retryLimit = 4;

    public static class Problem {
        public int[][] map;
        public Coord start;
        public Coord goal;

        Problem(int[][] map, Coord start, Coord goal) {
            this.map = map;
            this.start = start;
            this.goal = goal;
        }

        public int[][] getMap() {
            return map;
        }

        public Coord getStart() {
            return start;
        }

        public Coord getGoal() {
            return goal;
        }
    }

    public MapGenerator() {

    }


    public List<Problem> generateProblems() {
        List<Problem> problem = new ArrayList<>();
        float noOfLandCoords = (int) (landRatio * NO_RANDOM_MAPS);

        for (int i = 0; i < NO_RANDOM_MAPS; i++) {
            // generate map
            int rows = getRandomInt(MIN_MAP_SIZE, MAX_MAP_SIZE);
            int columns = getRandomInt(MIN_MAP_SIZE, MAX_MAP_SIZE);
            int[][] generatedMap = new int[rows][columns];

            // generate start and goal coordinates
            int startR = getRandomInt(MIN_MAP_SIZE - 1, rows - 1);
            int goalR = getRandomInt(MIN_MAP_SIZE - 1, rows - 1);
            int startC = getRandomInt(MIN_MAP_SIZE - 1, columns - 1);
            int goalC = getRandomInt(MIN_MAP_SIZE - 1, columns - 1);
            Coord init = new Coord(startR, startC);
            Coord goal = new Coord(goalR, goalC);

            // generate random land points
            for (int j = 0; j < noOfLandCoords; j++) {
                generateRandomLand(generatedMap, init, goal);
            }
            problem.add(new Problem(generatedMap, init, goal));
        }
        return problem;
    }

    public int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private void generateRandomLand(int[][] map, Coord start, Coord goal) {
        generateRandomLand(map, start, goal, 4);
    }

    private void generateRandomLand(int[][] map, Coord start, Coord goal, int retries) {
        int r = getRandomInt(MIN_MAP_SIZE - 1, map.length - 1);
        int c = getRandomInt(MIN_MAP_SIZE - 1, map[0].length - 1);
        if (map[r][c] == 0 && r != start.getR() && r != goal.getR() && c != start.getC() && c != goal.getC()) {
            map[r][c] = 1;
        } else {
            if (!(retries < 1)) {
                generateRandomLand(map, start, goal, retries - 1);
            }
        }

    }
}
