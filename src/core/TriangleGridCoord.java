package core;

public class TriangleGridCoord {
    private final int a;
    private final int b;
    private final int c;

    public TriangleGridCoord(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    public static TriangleGridCoord convertFromCoord(Coord coord) {
        int dir = (coord.getR() + coord.getC()) % 2 == 0 ? 0 : 1;
        int row = coord.getR();
        int col = coord.getC();
        int a = -row;
        int b = (row + col - dir) / 2;
        int c = b - row - dir;
        return new TriangleGridCoord(a, b, c);
    }
}
