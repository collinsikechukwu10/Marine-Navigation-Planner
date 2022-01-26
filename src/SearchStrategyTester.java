import core.Coord;
import core.Map;
import strategy.SearchStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class SearchStrategyTester {
    private int numberOfRandomMaps = 50;
    private int seed = 212;

    SearchStrategyTester() {

    }


    private void createRandomMaps() {
        Random random = new Random(seed);
        ArrayList<int[][]> generatedMaps = new ArrayList<>();
        ArrayList<Coord> inits = new ArrayList<>();
        ArrayList<Coord> goals = new ArrayList<>();
        int[] start = random.ints(numberOfRandomMaps, 3, 50).toArray();
        int[] end = random.ints(numberOfRandomMaps, 3, 50).toArray();
        for (int i = 0; i < numberOfRandomMaps; i++) {
            int rows = start[i];
            int columns = end[i];
            int[][] generatedMap = new int[rows][columns];
            int min = Math.min(rows, columns);
            int max = Math.max(rows, columns);
            // create random start and end coordinates.
            int[] randomPoints = random.ints(4, min, max).toArray();
            Coord init = new Coord(randomPoints[0], randomPoints[1]);
            Coord goal = new Coord(randomPoints[2], randomPoints[3]);
            random.longs((long) rows * columns, 0, 100).mapToObj(v -> v > 50).forEach();
            // add random land in the map
            float landVisibilityRatio = (float) (random.nextInt(50) / 100.0);

            inits.add(init);
            goals.add(goal);
            generatedMaps.add(generatedMap);
        }

    }
    private List<Class<SearchStrategy>> getStrategies(){
        return List.of();
    }

    private static void runSearch(ArrayList<int[][]> maps, ArrayList<Coord> starts, ArrayList<Coord> goals) {
        for (int i = 0; i < maps.size(); i++) {
            int[][] map = maps.get(i);

            System.out.println("Running Map " + (i + 1) + "..... size:[" + map.length + "," + map[0].length + "]" +
                    ", start:" + starts.get(i) + ", goal:" + goals.get(i));


        }

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
                } else {
                    if (isCoord(goal, r, c)) {
                        System.out.print("G");
                    } else {
                        if (map[r][c] == 0) {
                            System.out.print(".");
                        } else {
                            System.out.print(map[r][c]);
                        }
                    }
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

}
