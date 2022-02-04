package core;

/**
 * Triangle Grid Coordinate class.
 * This is based on the Square Grid system.
 *
 * @author 210032207
 * @version 1.0.0
 * @since 30-01-2022
 */
public class TriangleGridCoord {
    private final int a;
    private final int b;
    private final int c;

    /**
     * Constructor specifying the coordinates of a triangle grid coordinate
     *
     * @param a A value
     * @param b B value
     * @param c C value
     */
    public TriangleGridCoord(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Convert square grid coordinate to a triangle grid coordinate.
     *
     * @param coord square grid coordinate
     * @return triangle coordinate
     */
    public static TriangleGridCoord convertFromCoord(Coord coord) {
        int dir = (coord.getR() + coord.getC()) % 2 == 0 ? 0 : 1;
        int row = coord.getR();
        int col = coord.getC();
        int a = -row;
        int b = (row + col - dir) / 2;
        int c = b - row - dir;
        return new TriangleGridCoord(a, b, c);
    }

    /**
     * @return get A value
     */
    public int getA() {
        return a;
    }

    /**
     * @return get B value
     */
    public int getB() {
        return b;
    }

    /**
     * @return get C value
     */
    public int getC() {
        return c;
    }
}
