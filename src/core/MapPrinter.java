package core;

/**
 * Map printer class.
 * Used for debugging purposes.
 *
 * @author 210032207
 * @version 1.0.0
 * @since 30-01-2022
 */
public class MapPrinter {
    /**
     * Prints the problem map
     */
    public static void printMap(int[][] map, Coord start, Coord goal) {
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
                if (isCoord(start, r, c)) {
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
    private static String flip(boolean right) {
        return (right) ? "\\" : "/";
    }
}
